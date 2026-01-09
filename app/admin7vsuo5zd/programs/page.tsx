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
    { label: "Program", accessor: (row) => row.name },
    { label: "Department", accessor: (row) => row.department?.name }
  ]}
  searchOptions={[
    { label: "Program", value: "name" },
    { label: "Department", value: "department" }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => {
  const [departments, setDepartments] = useState<Record<string, any>[]>([]);
  
  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const departmentsRaw = await apiFetch("departments?all=true", {});
        setDepartments(toSelectOptions(departmentsRaw?.content, "id", "name"));
      } catch (error) {
        console.error(error);
      }
    }
    fetchDepartments();
  }, [])

  return (
    <>
      <InputGroup fieldName="name" label="Name" required />
      <SelectGroup fieldName="departmentId" label="Department" options={departments} required />
    </>
  );
}
  

export default ProgramsPage;
