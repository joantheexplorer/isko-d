"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import SelectGroup from "@/src/components/forms/SelectGroup";
import ResourcePage from "@/src/components/ResourcePage";
import { userSchema } from "@/src/schemas/userSchema";
import apiFetch from "@/src/utils/apiFetch";
import toSelectOptions from "@/src/utils/toSelectOptions";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

const UserPage = () => {
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
      resource={"users"}
      formSchema={userSchema}
      fields={[
        { 
          label: "ID",
          accessor: (row) => { 
            if (row.role?.name === "STUDENT") return row.barcode;
            return "";
          }
        },
        { label: "Name", accessor: (row) => `${row.firstName}${row.middleName ? `${row.middleName[0]}. ` : " "}${row.lastName}` },
        { label: "Role", accessor: (row) => row.role?.name } 
      ]}
      searchOptions={[
        { label: "First Name", value: "firstName" },
        { label: "Middle Name", value: "middleName" },
        { label: "Last Name", value: "lastName" },
        { label: "ID", value: "barcode" },
        { label: "Email", value: "email" },
        { label: "Role", value: "role" },
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
        const rolesRaw = await apiFetch("roles?all=true", {});
        setRoles(toSelectOptions(rolesRaw?.content, "id", "name"));
      } catch (error) {
        console.error(error);
      }
    }
    fetchRoles();
  }, [])

  return (
    <>
      <InputGroup fieldName="barcode" label="ID" />
      <InputGroup fieldName="firstName" label="First Name" required />
      <InputGroup fieldName="middleName" label="Middle Name" />
      <InputGroup fieldName="lastName" label="Last Name" required />
      <InputGroup fieldName="email" label="Email" type="email" required />
      <InputGroup fieldName="password" label="Password" type="password" required />
      <SelectGroup fieldName="roleId" label="Role" options={roles} required />
    </>
  );
}
  

export default UserPage;
