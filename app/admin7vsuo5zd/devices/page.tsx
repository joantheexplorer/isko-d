"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import SelectGroup from "@/src/components/forms/SelectGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { useResourceContext } from "@/src/contexts/ResourceContext";
import { deviceSchema } from "@/src/schemas/deviceSchema";
import apiFetch from "@/src/utils/apiFetch";
import toSelectOptions from "@/src/utils/toSelectOptions";
import { useEffect, useState } from "react";

const DevicesPage = () => {
  const { lastCreatedToken, setLastCreatedToken } = useResourceContext();

  const handleClose = () => {
    setLastCreatedToken("");
  }

  return (
    <>
      <ResourcePage
        resource={"devices"}
        formSchema={deviceSchema}
        fields={[
          { label: "Device", accessor: (row) => row.name },
          { label: "Location", accessor: (row) => row.location?.name }
        ]}
        searchOptions={[
          { label: "Device", value: "name" },
          { label: "Location", value: "location" },
        ]}
        FormInputs={FormInputs}
      />
      { lastCreatedToken && 
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
          <div
            className="w-full max-w-3xl rounded-lg bg-white shadow-lg"
          >
            <div className="flex items-center justify-between px-5 py-3">
              <h2 className="text-lg font-semibold">Device Token</h2>
            </div>

            <div className="px-5 py-4 flex flex-col gap-2">
              <strong>This token contains sensitive information and must not be shared.</strong>
              <strong>You may only view this token once.</strong>
              <p>Please administer this token to the according kiosk device.</p>
              <pre className="font-mono p-2 bg-red-200">{lastCreatedToken}</pre>
            </div>

            <div className="flex flex-row-reverse px-5 py-3 gap-2">
              <button
                onClick={handleClose}
                className="bg-red-700 rounded-sm text-white hover:bg-red-600 p-2"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      }
    </>
  );
} 

const FormInputs = () => {
  const [locations, setLocations] = useState<Record<string, any>[]>([]);
  
  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const locationsRaw = await apiFetch("locations?all=true", {});
        setLocations(toSelectOptions(locationsRaw?.content, "id", "name"));
      } catch (error) {
        console.error(error);
      }
    }
    fetchLocations();
  }, [])

  return (
    <>
      <InputGroup fieldName="name" label="Name" required />
      <SelectGroup fieldName="locationId" label="Location" options={locations} required />
    </>
  );
}
  

export default DevicesPage;
