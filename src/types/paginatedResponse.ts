export type PaginatedResponse = {
    content: Record<string, any>[],
    page: number,
    size: number,
    totalElements: number,
    totalPages: number,
    last: boolean
}
