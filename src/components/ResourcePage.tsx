import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { useResourceContext } from "../contexts/ResourceContext";
import { zodResolver } from "@hookform/resolvers/zod";
import z, { ZodType } from "zod";
import { ReactNode, useEffect, useState } from "react";
import { PlusIcon } from "@heroicons/react/24/outline";
import DataTable from "./DataTable";
import FormModal from "./forms/FormModal";
import Select, { SingleValue } from "react-select";
import { ApiError } from "../types/ApiError";
import { useRouter } from "next/navigation";

type Option = { label: string; value: string; };

type Props = {
  formSchema: ZodType<any, any>;
  resource: string;
  fields: { label: string; accessor: (row: Record<string, any>) => string; }[];
  searchOptions: Option[];
  FormInputs: () => ReactNode;
  noActions?: boolean;
}

const ResourcePage = ({ formSchema, resource, fields, searchOptions, FormInputs, noActions }: Props) => {
  type FormValues = z.infer<typeof formSchema>;

  const [searchInput, setSearchInput] = useState("");
  const [searchBy, setSearchBy] = useState("name");

  const router = useRouter();

  const { 
    loadResource,
    openCreate,
    isModalOpen, 
    closeModal,
    submitItem,
    setListParams
  } = useResourceContext();

  useEffect(() => {
    const handler = setTimeout(() => {
      setListParams((prev) => ({
        ...prev,
        search: searchInput,
        searchBy: searchBy || "name",
        page: 0,
      }));
    }, 500);

  return () => clearTimeout(handler);
}, [searchInput]);

  const methods = useForm<FormValues>({
    resolver: zodResolver(formSchema)
  });

  useEffect(() => {
    loadResource(resource);
  }, [])

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    await submitItem(data);
    methods.reset();
  }

  const handleClose = () => {
    closeModal();
    methods.reset();
  }

  const handleSearch = (e: any) => {
    setSearchInput(e.target.value);
  }

  const handleSearchBy = (option: SingleValue<Option>) => {
    const value = option?.value || "";
    setSearchBy(value);
    setListParams((prev) => ({ ...prev, searchBy: value, page: 0 }));
  }

  return (
    <div className="p-6">
      <div className="flex flex-row items-end justify-between pb-3">
        <h1 className="text-4xl font-extrabold mt-5">{resource[0].toUpperCase() + resource.slice(1)}</h1>
        <div className="flex flex-row gap-2">
          <input 
            className=" bg-white border border-gray-300 rounded px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-1 focus:ring-red-500 focus:border-red-500 w-48 hover:ring-red-300 hover:border-red-300 transition-colors"
            type="text"
            value={searchInput || ""}
            onChange={handleSearch}
            placeholder="Search..."
          />
          <Select<Option, false>
            styles={{
              control: (base, state) => ({
                ...base,
                borderColor: state.isFocused ? '#ef4444' : base.borderColor,
                boxShadow: state.isFocused ? '0 0 0 1px #ef4444' : 'none',
                '&:hover': {
                  borderColor: '#ef4444',
                },
              }),
            }}
            options={searchOptions}
            isClearable
            value={searchOptions.find((opt) => opt.value === searchBy) || null}
            onChange={handleSearchBy}
            placeholder="Search By..."
          />
          { !noActions &&
            <button className="p-2 flex rounded text-white bg-red-700 hover:bg-red-600 transition-colors" onClick={openCreate}>
              <PlusIcon className="size-6"/> 
              <span>&nbsp;Add item&nbsp;</span>
            </button>
          }
        </div>
      </div>
      <FormProvider {...methods}>
        <div className="overflow-x-auto">
          <DataTable fields={fields} withActions={!noActions}/>
        </div>
        <FormModal 
          isOpen={isModalOpen}
          title={resource[0].toUpperCase() + resource.slice(1)}
          handleClose={handleClose}
          formInputs={
              <form id="modal-form" onSubmit={methods.handleSubmit(onSubmit)}>
                <FormInputs />
              </form>
          }
        />
      </FormProvider>
    </div>
  );
}

export default ResourcePage;
