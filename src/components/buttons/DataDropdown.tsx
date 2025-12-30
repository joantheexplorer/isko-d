import { EllipsisVerticalIcon, PencilIcon, TrashIcon } from "@heroicons/react/24/outline";

const DataDropdown = () => {
  return (
    <div className="flex gap-2">
      <PencilIcon className="size-6 text-gray-800 ml-auto hover:text-red-600 transition-colors cursor-pointer" />
      <TrashIcon className="size-6 text-gray-800 ml-auto hover:text-red-600 transition-colors cursor-pointer" />
    </div>
  )
}

export default DataDropdown;
