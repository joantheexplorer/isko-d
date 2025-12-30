"use client";

import DataTable from "@/src/components/DataTable";
import { useResourceContext } from "@/src/contexts/ResourceContext";
import { useEffect } from "react";

const fields = [
  { label: "ID", identifier: "id" },
  { label: "Action", identifier: "name" }
];

const ActionsPage = () => {
  const { loadResource, listData, isListDataLoading } = useResourceContext();

  useEffect(() => {
    loadResource("actions");
  }, [])

  return (
    <div className="p-6">
      <h1 className="text-4xl font-extrabold mt-5 mb-6">Actions</h1>

      <div className="overflow-x-auto">
        <DataTable fields={fields} data={listData} isLoading={isListDataLoading} withActions={true}/>
      </div>
    </div>
  );
}

export default ActionsPage;
