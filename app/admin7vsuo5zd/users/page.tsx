"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import SelectGroup from "@/src/components/forms/SelectGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { useResourceContext } from "@/src/contexts/ResourceContext";
import { userSchema } from "@/src/schemas/userSchema";
import apiFetch from "@/src/utils/apiFetch";
import toSelectOptions from "@/src/utils/toSelectOptions";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

const UserPage = () => {
  const router = useRouter();
  const isSuperAdmin = localStorage.getItem("user_role") == "SUPERADMIN";

  if (!isSuperAdmin) router.push("/admin")

  return (
    <ResourcePage
      resource={"users"}
      formSchema={userSchema}
      fields={[
        { label: "ID", accessor: (row) => row.barcode ?? row.id },
        { label: "Name", accessor: (row) => `${row.firstName} ${row.lastName}` },
        { label: "Role", accessor: (row) => row.role.name } 
      ]}
      FormInputs={FormInputs}
    />
  );
}

const FormInputs = () => {
  const [roles, setRoles] = useState<Record<string, any>[]>([]);
  
  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const rolesRaw = await apiFetch("roles", {});
        setRoles(toSelectOptions(rolesRaw, "id", "name"));
      } catch (error) {
        console.error(error);
      }
    }
    fetchRoles();
  }, [])

  return (
    <>
      <InputGroup fieldName="barcode" label="ID" />
      <InputGroup fieldName="firstName" label="First Name" />
      <InputGroup fieldName="middleName" label="Middle Name" />
      <InputGroup fieldName="lastName" label="Last Name" />
      <InputGroup fieldName="email" label="Email" />
      <InputGroup fieldName="password" label="Password" />
      <SelectGroup fieldName="roleId" label="Role" options={roles} />
    </>
  );
}
  

export default UserPage;
