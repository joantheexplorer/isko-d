"use client";

import ResourcePage from "@/src/components/ResourcePage";
import { dummySchema } from "@/src/schemas/dummySchema";

const DashboardPage = () => <ResourcePage 
  resource={"logs"}
  formSchema={dummySchema}
  fields={[
  { label: "ID", accessor: (row) => row.user?.barcode },
  { label: "Action", accessor: (row) => row.action?.name },
  { label: "Location", accessor: (row) => row.device?.location?.name },
  { label: "Device", accessor: (row) => row.device?.name },
  { label: "Timestamp", accessor: (row) => new Date(row.createdAt).toLocaleString() },
  ]}
  searchOptions={[
    { label: "ID", value: "barcode" },
    { label: "Action", value: "action" },
    { label: "Location", value: "location" },
    { label: "Device", value: "device" }
  ]}
  FormInputs={() => <></>}
  noActions={true}
/>

export default DashboardPage;
