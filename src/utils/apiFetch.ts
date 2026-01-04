import { ApiError } from "../types/ApiError";

const apiFetch = async (
  resource: string,
  options: Record<string, any>
) => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}${resource}`, {
    method: options.method ?? "GET",
    headers: {
      "Content-Type": "application/json",
      ...options?.headers,
    },
    credentials: "include",
    ...options,
    body: options.body ? JSON.stringify(options.body) : undefined,
  });

  const isJson = res.headers.get("Content-Type")?.includes("application/json");

  const body = isJson ? await res.json() : await res.text();

  if (!res.ok) throw new ApiError(res.status, body);

  return body;
}

export default apiFetch;
