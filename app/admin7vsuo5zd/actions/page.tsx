"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { actionSchema } from "@/src/schemas/actionSchema";

const ActionsPage = () => <ResourcePage
  resource={"actions"}
  formSchema={actionSchema}
  fields={[
    { label: "ID", identifier: "id" },
    { label: "Action", identifier: "name" }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" />
  </>
);
  

export default ActionsPage;
