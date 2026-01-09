"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { actionSchema } from "@/src/schemas/actionSchema";

const ActionsPage = () => <ResourcePage
  resource={"actions"}
  formSchema={actionSchema}
  fields={[
    { label: "Action", accessor: (row) => row.name }
  ]}
  searchOptions={[
    { label: "Action", value: "name" }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" required />
  </>
);
  

export default ActionsPage;
