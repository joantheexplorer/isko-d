"use client";

import apiFetch from "@/src/utils/apiFetch";
import { useRouter } from "next/navigation";

const LogoutButton = () => {
  const router = useRouter();

  const handleLogout = async () => {
    try {
      apiFetch("auth/logout", { method: "POST" });
      router.push("/admin7vsuo5zd/login");
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <button className="px-3 py-1 bg-red-900 text-white rounded hover:bg-red-800" onClick={handleLogout}>
      Logout
    </button>
  );
}

export default LogoutButton;
