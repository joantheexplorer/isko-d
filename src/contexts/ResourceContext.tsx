"use client";

import { createContext, Dispatch, ReactNode, SetStateAction, useContext, useEffect, useState } from "react";
import apiFetch from "../utils/apiFetch";
import { ApiError } from "../types/ApiError";
import { PaginatedResponse } from "../types/paginatedResponse";
import { useRouter } from "next/navigation";

export type ResourceContextType = {
  setResource: Dispatch<SetStateAction<string>>;
  setListParams: Dispatch<SetStateAction<Record<string, any>>>;
  setSelectedParams: Dispatch<SetStateAction<Record<string, any>>>;
  setLastCreatedToken: Dispatch<SetStateAction<string>>;

  lastCreatedToken: string;

  listData: PaginatedResponse;
  selectedData: Record<string, any>;

  mode: "EDIT" | "CREATE" | "VIEW" | "";

  isListDataLoading: boolean;
  isSelectedDataLoading: boolean;
  isModalOpen: boolean;
  isFormDisabled: boolean;

  openCreate: () => void;
  openEdit: (item: Record<string, any>) => void;
  openView: (item: Record<string, any>) => void;
  closeModal: () => void;

  fetchList: () => void;
  loadResource: (res: string) => void;

  submitItem: (data: Record<string, any>) => Promise<void>;
  deleteItem: (id: number) => Promise<void>;
};

export const ResourceContext = createContext<ResourceContextType | null>(null);

export const useResourceContext = () => {
  const context = useContext(ResourceContext);
  if (!context) throw new Error ("useResourceContext must be used inside ResourceContextProvider");
  return context;
}

export const ResourceContextProvider = ({ children }: { children: ReactNode }) => {
  const router = useRouter();

  const [resource, setResource] = useState<string>("");
  const [mode, setMode] = useState<"EDIT" | "CREATE" | "VIEW" | "">("");
  const [listParams, setListParams] = useState<Record<string, any>>({});
  const [selectedParams, setSelectedParams] = useState<Record<string, any>>({});

  const [listData, setListData] = useState<PaginatedResponse>({
    content: [],
    page: 0,
    size: 0,
    totalElements: 0,
    totalPages: 0,
    last: true
  });
  const [selectedData, setSelectedData] = useState<Record<string, any>>({});

  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [isFormDisabled, setIsFormDisabled] = useState<boolean>(false);
  const [isListDataLoading, setIsListDataLoading] = useState<boolean>(false);
  const [isSelectedDataLoading, setIsSelectedDataLoading] = useState<boolean>(false);

  const [lastCreatedToken, setLastCreatedToken] = useState<string>("");

  useEffect(() => {
    if (resource) fetchList();
  }, [listParams]);

  const openCreate = () => {
    setSelectedData({});
    setMode("CREATE");
    setIsFormDisabled(false);
    setIsModalOpen(true);
  }

  const openEdit = (item: Record<string, any>) => {
    setSelectedData(item);
    fetchItem(item.id);
    setMode("EDIT");
    setIsFormDisabled(false);
    setIsModalOpen(true);
  }

  const openView = (item: Record<string, any>) => {
    setSelectedData(item);
    fetchItem(item.id);
    setMode("VIEW");
    setIsFormDisabled(true);
    setIsModalOpen(true);
  }

  const closeModal = () => {
    setSelectedData({});
    setMode("");
    setIsModalOpen(false);
  }

  const fetchList = async () => {
    setIsListDataLoading(true);

    const searchParams = new URLSearchParams();
    Object.entries(listParams).forEach(([key, value]) => {
      if (value !== undefined && value !== "") {
        searchParams.append(key, String(value));
      }
    });

    const data = await apiFetch(
      `${resource}?${searchParams.toString()}`,
      {}
    );
    setListData(data);

    setIsListDataLoading(false);
  }

  const fetchItem = async (id: number | string) => {
    setIsSelectedDataLoading(true);

    const searchParams = new URLSearchParams();
    Object.entries(selectedParams).forEach(([key, value]) => {
      if (value !== undefined && value !== "") {
        searchParams.append(key, String(value));
      }
    });

    const data = await apiFetch(
      `${resource}/${id}?${searchParams.toString()}`,
      {}
    );
    setSelectedData(data);

    setIsSelectedDataLoading(false);
  }

  const loadResource = async (res: string) => {
    setResource(res);
    setIsListDataLoading(true);

    const searchParams = new URLSearchParams();
    Object.entries(listParams).forEach(([key, value]) => {
      if (value !== undefined && value !== "") {
        searchParams.append(key, String(value));
      }
    });

    try {
      const data = await apiFetch(
        `${res}?${searchParams.toString()}`,
        {}
      );
      setListData(data);
      setIsListDataLoading(false);
    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 204) {
          setIsListDataLoading(false);
        } else if (error.status === 401) {
          apiFetch("auth/logout", { method: "POST" });
          router.push("/admin7vsuo5zd/login");
        }
      }
    }
  }

  const submitItem = async (data: Record<string, any>) => {
    try {
      const res = await apiFetch(mode == "CREATE" ? resource : `${resource}/${selectedData.id}`, {
        method: mode === "CREATE" ? "POST" : "PATCH",
        body: data
      });

      await fetchList();
      setIsModalOpen(false);

      if (res?.plainToken) setLastCreatedToken(res.plainToken);
    } catch (error) {
      console.error(error);
    }
  }

  const deleteItem = async (id: number) => {
    await apiFetch(`${resource}/${id}`, {
      method: "DELETE"
    });
    await fetchList();
  }

  return (
    <ResourceContext.Provider
      value={{
        setResource,
        setListParams,
        setSelectedParams,
        setLastCreatedToken,

        lastCreatedToken,

        listData,
        selectedData,

        mode,
        isListDataLoading,
        isSelectedDataLoading,
        isModalOpen,
        isFormDisabled,

        fetchList,
        loadResource,

        openCreate,
        openEdit,
        openView,
        closeModal,

        submitItem,
        deleteItem,
      }}
    >
      {children}
    </ResourceContext.Provider>
  )
}
