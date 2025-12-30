"use client";

import { createContext, Dispatch, ReactNode, SetStateAction, useContext, useState } from "react";
import apiFetch from "../utils/apiFetch";

export type ResourceContextType = {
  setResource: Dispatch<SetStateAction<string>>;
  setListParams: Dispatch<SetStateAction<Record<string, any>>>;
  setSelectedParams: Dispatch<SetStateAction<Record<string, any>>>;

  listData: Record<string, any>[];
  selectedData: Record<string, any>;

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

  deleteItem: (id: number) => Promise<void>;
};

export const ResourceContext = createContext<ResourceContextType | null>(null);

export const useResourceContext = () => {
  const context = useContext(ResourceContext);
  if (!context) throw new Error ("useResourceContext must be used inside ResourceContextProvider");
  return context;
}

export const ResourceContextProvider = ({ children }: { children: ReactNode }) => {
  const [resource, setResource] = useState<string>("");
  const [listParams, setListParams] = useState<Record<string, any>>({});
  const [selectedParams, setSelectedParams] = useState<Record<string, any>>({});

  const [listData, setListData] = useState<Record<string, any>[]>([]);
  const [selectedData, setSelectedData] = useState<Record<string, any>>({});

  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [isFormDisabled, setIsFormDisabled] = useState<boolean>(false);
  const [isListDataLoading, setIsListDataLoading] = useState<boolean>(false);
  const [isSelectedDataLoading, setIsSelectedDataLoading] = useState<boolean>(false);

  const openCreate = () => {
    setSelectedData({});
    setIsFormDisabled(false);
    setIsModalOpen(true);
  }

  const openEdit = (item: Record<string, any>) => {
    setSelectedData(item);
    fetchItem(item.id);
    setIsFormDisabled(false);
    setIsModalOpen(true);
  }

  const openView = (item: Record<string, any>) => {
    setSelectedData(item);
    fetchItem(item.id);
    setIsFormDisabled(true);
    setIsModalOpen(true);
  }

  const closeModal = () => {
    setSelectedData({});
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

    const data = await apiFetch(
      `${res}?${searchParams.toString()}`,
      {}
    );

    setListData(data);

    setIsListDataLoading(false);
  }

  const deleteItem = async (id: number) => {
    await apiFetch(`${resource}/${id}`, {});
    await fetchList();
  }

  return (
    <ResourceContext.Provider
      value={{
        setResource,
        setListParams,
        setSelectedParams,

        listData,
        selectedData,

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

        deleteItem,
      }}
    >
      {children}
    </ResourceContext.Provider>
  )
}
