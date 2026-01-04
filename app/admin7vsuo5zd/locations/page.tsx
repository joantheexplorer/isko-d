"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { locationSchema } from "@/src/schemas/locationSchema";

const LocationsPage = () => <ResourcePage
  resource={"locations"}
  formSchema={locationSchema}
  fields={[
    { label: "ID", accessor: (row) => row.id },
    { label: "Location", accessor: (row) => row.name }
  ]}
  FormInputs={FormInputs}
/>

const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" />
  </>
);
  

export default LocationsPage;
