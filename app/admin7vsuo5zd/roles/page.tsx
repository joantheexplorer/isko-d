"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { roleSchema } from "@/src/schemas/roleSchema";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

const RolesPage = () => {
  const router = useRouter();
  const [isSuperAdmin, setIsSuperAdmin] = useState(false);

  useEffect(() => {
    const userRole = localStorage.getItem("user_role")
    setIsSuperAdmin(userRole === "SUPERADMIN");
    if (userRole !== "SUPERADMIN") router.push("/admin7vsuo5zd");
  }, [])

  if (!isSuperAdmin) return null;
  return (
    <ResourcePage
      resource={"roles"}
      formSchema={roleSchema}
      fields={[
        { label: "Role", accessor: (row) => row.name }
      ]}
      searchOptions={[
        { label: "Role", value: "name" }
      ]}
      FormInputs={FormInputs}
    />
  );
} 
const FormInputs = () => (
  <>
    <InputGroup fieldName="name" label="Name" required />
  </>
);
  

export default RolesPage;
