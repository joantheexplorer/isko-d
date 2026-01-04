"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { departmentSchema } from "@/src/schemas/departmentSchema";

const DepartmentsPage = () => <ResourcePage
  resource={"departments"}
  formSchema={departmentSchema}
  fields={[
    { label: "ID", accessor: (row) => row.id },
    { label: "Department", accessor: (row) => row.name }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" />
  </>
);
  

export default DepartmentsPage;
