import { useResourceContext } from "@/src/contexts/ResourceContext";
import { PencilIcon, TrashIcon } from "@heroicons/react/24/outline";
import { useFormContext } from "react-hook-form";

const DataDropdown = ({item}: { item: Record<string, any> }) => {
  const { deleteItem, openEdit } = useResourceContext();
  const { reset } = useFormContext();

  const handleEdit = () => {
    reset(() => {
      const result: Record<string, any> = { ...item };

      Object.entries(item).forEach(([key, value]) => {
        if (value && typeof value === "object" && "id" in value) {
          result[`${key}Id`] = value.id;
        }
      });

      return result;
    });
    openEdit(item);
  }

  const handleDelete = () => {
    deleteItem(item.id);
  }

  return (
    <div className="flex gap-2">
      <PencilIcon 
        className="size-6 text-gray-800 ml-auto hover:text-red-600 transition-colors cursor-pointer"
        onClick={handleEdit}
      />
      <TrashIcon 
        className="size-6 text-gray-800 ml-auto hover:text-red-600 transition-colors cursor-pointer"
        onClick={handleDelete}
      />
    </div>
  )
}

export default DataDropdown;
