"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { departmentSchema } from "@/src/schemas/departmentSchema";

const DepartmentsPage = () => <ResourcePage
  resource={"departments"}
  formSchema={departmentSchema}
  fields={[
    { label: "Department", accessor: (row) => row.name }
  ]}
  searchOptions={[
    { label: "Department", value: "name" }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" required />
  </>
);
  

export default DepartmentsPage;
