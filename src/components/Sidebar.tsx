"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";

const sidebarOptions = [
  { label: "Dashboard", href: "", isHighlighted: false },
  { label: "Actions", href: "/actions", isHighlighted: false },
  { label: "Locations", href: "/locations", isHighlighted: false },
  { label: "Devices", href: "/devices", isHighlighted: false },
  { label: "Departments", href: "/departments", isHighlighted: false },
  { label: "Programs", href: "/programs", isHighlighted: false },
  { label: "Users", href: "/users", isHighlighted: false },
  { label: "Roles", href: "/roles", isHighlighted: false },
]

const Sidebar = () => {
  const pathname = usePathname();
  const [isSuperAdmin, setIsSuperAdmin] = useState<boolean>(false);

  useEffect(() => {
    setIsSuperAdmin(localStorage.getItem("user_role") == "SUPERADMIN");
  }, [])

  return (
    <aside className="hidden md:flex md:flex-col w-64 text-white">
      <nav className="flex-1 p-4 space-y-2 bg-red-800">
        { sidebarOptions.map((option, idx) => {
          const href = `/admin7vsuo5zd${option.href}`;
          const isHighlighted = href === pathname;
          
          if (!isSuperAdmin && option.label == "Users") return null;
          if (!isSuperAdmin && option.label == "Roles") return null;

          return (
            <Link 
              className={`block px-4 py-2 rounded hover:bg-red-700 ${isHighlighted ? "bg-red-700" : ""} transition-colors`}
              key={`sidebarOption${idx}`}
              href={href}
            >
              {option.label}
            </Link>
          );
        })}
      </nav>
    </aside>
  )
}

export default Sidebar;
