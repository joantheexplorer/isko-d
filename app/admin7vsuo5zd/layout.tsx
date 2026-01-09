import LogoutButton from "@/src/components/forms/LogoutButton";
import Sidebar from "@/src/components/Sidebar";
import { ResourceContextProvider } from "@/src/contexts/ResourceContext";
import { UserIcon } from "@heroicons/react/24/outline";
import { cookies } from "next/headers";
import Image from "next/image";
import { redirect } from "next/navigation";
import { ReactNode } from "react";

const AdminLayout = async ({ children }: { children: ReactNode }) => {
  const userCookies = await cookies();
  const token = userCookies.get("api_token");

  if (!token) redirect("/admin7vsuo5zd/login");

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      <header className="h-12 bg-white shadow-md flex items-center justify-between px-6">
        <div className="text-xl font-semibold text-gray-800 flex flex-row items-center gap-2">
          <Image src="/assets/logo.png" alt="Logo" width={44} height={22} className="-ml-4" />
          <span>Isko'D: One ID, All Access</span>
        </div>
        <div className="flex items-center space-x-4">
          <div className="w-10 h-10 rounded-full bg-gray-300 flex items-center justify-center text-gray-700">
            <UserIcon className="size-6" />
          </div>
          <LogoutButton />
        </div>
      </header>

      <div className="flex flex-1">
        <Sidebar />

        <main className="flex-1 p-6 overflow-auto">
          <ResourceContextProvider>
            {children}
          </ResourceContextProvider>
        </main>
      </div>
    </div>
  );
};

export default AdminLayout;
