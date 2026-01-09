import DataDropdown from "./buttons/DataDropdown";
import { useResourceContext } from "../contexts/ResourceContext";
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon, ChevronLeftIcon, ChevronRightIcon } from "@heroicons/react/24/outline";

type Props = {
  fields: { label: string, accessor: (row: Record<string, any>) => string }[],
  withActions?: boolean,
}

const DataTable = ({ fields, withActions }: Props) => {
  const { listData, setListParams, isListDataLoading } = useResourceContext();
  const handlePageChange = (newPage: number) => {
    setListParams((prev) => ({ ...prev, page: newPage }));
  }

  return (
    <div>
      <table className="min-w-full border border-gray-200 shadow-md rounded-lg overflow-auto">
        <thead className="bg-red-800 text-white">
          <tr>
          {fields.map((field, idx) => (
            <th key={`field${idx}`} className="px-4 py-2 text-left font-bold text-md">{field.label}</th>
          ))}
          {withActions && <th className="w-16"></th>}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-red-100">
          {isListDataLoading 
            ? 
              <tr className="hover:bg-red-100 transition-colors">
                <td
                  className="px-4 py-6 text-center"
                  colSpan={fields.length + (withActions ? 1 : 0)}
                >
                  <div className="flex justify-center items-center">
                    <svg
                      className="animate-spin h-12 w-12 text-red-500"
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                    >
                      <circle
                        className="opacity-25"
                        cx="12"
                        cy="12"
                        r="10"
                        stroke="currentColor"
                        strokeWidth="4"
                      ></circle>
                      <path
                        className="opacity-75"
                        fill="currentColor"
                        d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                      ></path>
                    </svg>
                  </div>
                </td>
              </tr>
            : listData.content?.length > 0
              ? (listData.content ?? []).map((entry: Record<string, any>, idx: number) => {
                  return (
                    <tr key={`row${idx}`} className="hover:bg-red-100 transition-colors">
                    {fields.map((field, idx2) => (
                      <td
                      key={`row${idx}${field.label}${idx2}`}
                      className="px-4 py-2 text-sm text-gray-800"
                      >
                        {field.accessor(entry)}
                      </td>
                    ))}
                    { withActions && <td className="px-4 py-2"><DataDropdown item={entry} /></td> }
                    </tr>
                  );
                }
              )
            : <tr className="hover:bg-red-100 transition-colors">
                <td className="px-4 py-2 text-md text-gray-800 text-center" colSpan={fields.length + (withActions ? 1 : 0)}>
                  No records
                </td>
              </tr>
        }
        </tbody>
      </table>
        <div className="flex justify-end space-x-2 mt-4 items-center mr-3">
          <button
            disabled={(listData.page ?? 0) === 0}
            onClick={() => handlePageChange(0)}
            className="text-red-700 font-black cursor-pointer disabled:opacity-50 disabled:cursor-default"
          >
            <ChevronDoubleLeftIcon className="size-6" />
          </button>
          <button
            disabled={(listData.page ?? 0) === 0}
            onClick={() => handlePageChange(listData.page - 1)}
            className="text-red-700 font-black cursor-pointer disabled:opacity-50 disabled:cursor-default"
          >
            <ChevronLeftIcon className="size-5" />
          </button>

          <span className="font-bold text-red-700">{(listData.page ?? 0) + 1}</span>

          <button
            disabled={(listData.page ?? 0) + 1 >= (listData.totalPages ?? 0)}
            onClick={() => handlePageChange(listData.page + 1)}
            className="text-red-700 font-black cursor-pointer disabled:opacity-50 disabled:cursor-default"
          >
            <ChevronRightIcon className="size-5" />
          </button>
          <button
            disabled={(listData.page ?? 0) + 1 >= (listData.totalPages ?? 0)}
            onClick={() => handlePageChange(listData.totalPages - 1)}
            className="text-red-700 font-black cursor-pointer disabled:opacity-50 disabled:cursor-default"
          >
            <ChevronDoubleRightIcon className="size-6" />
          </button>
        </div>
    </div>
  );
}

export default DataTable;
