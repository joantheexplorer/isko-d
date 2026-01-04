"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import SelectGroup from "@/src/components/forms/SelectGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { programSchema } from "@/src/schemas/programSchema";
import apiFetch from "@/src/utils/apiFetch";
import toSelectOptions from "@/src/utils/toSelectOptions";
import { useEffect, useState } from "react";

const ProgramsPage = () => <ResourcePage
  resource={"programs"}
  formSchema={programSchema}
  fields={[
    { label: "ID", accessor: (row) => row.id },
    { label: "Program", accessor: (row) => row.name }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => {
  const [departments, setDepartments] = useState<Record<string, any>[]>([]);
  
  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const departmentsRaw = await apiFetch("departments", {});
        setDepartments(toSelectOptions(departmentsRaw, "id", "name"));
      } catch (error) {
        console.error(error);
      }
    }
    fetchDepartments();
  }, [])

  return (
    <>
      <InputGroup fieldName="name" label="Name" />
      <SelectGroup fieldName="departmentId" label="Department" options={departments} />
    </>
  );
}
  

export default ProgramsPage;
