import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { useResourceContext } from "../contexts/ResourceContext";
import { zodResolver } from "@hookform/resolvers/zod";
import z, { ZodType } from "zod";
import { ReactNode, useEffect } from "react";
import { PlusIcon } from "@heroicons/react/24/outline";
import DataTable from "./DataTable";
import FormModal from "./forms/FormModal";

type Props = {
  formSchema: ZodType<any, any>;
  resource: string;
  fields: { label: string; identifier: string; }[];
  FormInputs: () => ReactNode;
}

const ResourcePage = ({ formSchema, resource, fields, FormInputs }: Props) => {
  type FormValues = z.infer<typeof formSchema>;

  const { 
    loadResource,
    openCreate,
    listData,
    isListDataLoading,
    isModalOpen, 
    closeModal,
    submitItem,
  } = useResourceContext();

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

  return (
    <div className="p-6">
      <div className="flex flex-row items-end justify-between pb-3">
        <h1 className="text-4xl font-extrabold mt-5">{resource[0].toUpperCase() + resource.slice(1)}</h1>
        <button className="p-2 flex rounded text-white bg-red-700 hover:bg-red-600" onClick={openCreate}>
          <PlusIcon className="size-6"/> 
          <span>&nbsp;Add item&nbsp;</span>
        </button>
      </div>
      <FormProvider {...methods}>
        <div className="overflow-x-auto">
          <DataTable fields={fields} data={listData} isLoading={isListDataLoading} withActions={true}/>
        </div>
        <FormModal 
          isOpen={isModalOpen}
          title={resource[0].toUpperCase() + resource.slice(1)}
          handleClose={closeModal}
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
