import { ApiError } from "../types/ApiError";

const apiFetch = async (
  resource: string,
  options: Record<string, any>
) => {
  const { method, headers, credentials, body, ...rest } = options;
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}${resource}`, {
    ...rest,
    method: method ?? "GET",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    credentials: credentials ?? "include",
    body: body ? JSON.stringify(body) : undefined,
  });

  let data = null;

  const contentType = res.headers.get("content-type");
  if (contentType?.includes("application/json")) {
    const text = await res.text();
    data = text ? JSON.parse(text) : null;
  }

  if (!res.ok) throw new ApiError(res.status, data);

  return data;
}

export default apiFetch;
