import apiFetch from "@/src/utils/apiFetch";

const dummyLogs = [
  {
    id: "2024-MN-0-02756",
    action: "Enter",
    device: "Kiosk-1",
    location: "Library",
    createdAt: "2024-12-29T01:00:00.000Z",
  },
  {
    id: "2024-MN-0-02757",
    action: "Exit",
    device: "Kiosk-1",
    location: "Library",
    createdAt: "2024-12-29T03:12:45.000Z",
  },
  {
    id: "2024-MN-0-02758",
    action: "Enter",
    device: "Kiosk-2",
    location: "Learning Commons",
    createdAt: "2024-12-29T05:30:10.000Z",
  },
  {
    id: "2024-MN-0-02759",
    action: "Exit",
    device: "Kiosk-2",
    location: "Learning Commons",
    createdAt: "2024-12-29T07:05:22.000Z",
  },
  {
    id: "2024-MN-0-02760",
    action: "Enter",
    device: "Gate-1",
    location: "Archives Section",
    createdAt: "2024-12-29T09:18:33.000Z",
  },
  {
    id: "2024-MN-0-02761",
    action: "Exit",
    device: "Gate-1",
    location: "Archives Section",
    createdAt: "2024-12-29T11:42:01.000Z",
  },
];

const DashboardPage = async () => {
  return (
    <div className="p-6">
      <h1 className="text-4xl font-extrabold mt-5 mb-6">Activity Logs</h1>

      <div className="overflow-x-auto">
        <table className="min-w-full border border-gray-200 shadow-md rounded-lg overflow-auto">
          <thead className="bg-red-800 text-white">
            <tr>
              <th className="px-4 py-2 text-left font-bold text-md">ID</th>
              <th className="px-4 py-2 text-left font-bold text-md">Action</th>
              <th className="px-4 py-2 text-left font-bold text-md">Device</th>
              <th className="px-4 py-2 text-left font-bold text-md">Location</th>
              <th className="px-4 py-2 text-left font-bold text-md">Date</th>
              <th className="px-4 py-2 text-left font-bold text-md">Time</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-red-100">
            {dummyLogs.map((log, idx) => {
              const dateObj = new Date(log.createdAt);
              const date = dateObj.toLocaleDateString();
              const time = dateObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

              return (
                <tr key={idx} className="hover:bg-red-100 transition-colors">
                  <td className="px-4 py-2 text-sm text-gray-800">{log.id}</td>
                  <td className="px-4 py-2 text-sm text-gray-800">{log.action}</td>
                  <td className="px-4 py-2 text-sm text-gray-800">{log.device}</td>
                  <td className="px-4 py-2 text-sm text-gray-800">{log.location}</td>
                  <td className="px-4 py-2 text-sm text-gray-800">{date}</td>
                  <td className="px-4 py-2 text-sm text-gray-800">{time}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default DashboardPage;
