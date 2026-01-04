"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { roleSchema } from "@/src/schemas/roleSchema";

const RolesPage = () => <ResourcePage
  resource={"roles"}
  formSchema={roleSchema}
  fields={[
    { label: "ID", accessor: (row) => row.id },
    { label: "Role", accessor: (row) => row.name }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" />
  </>
);
  

export default RolesPage;
