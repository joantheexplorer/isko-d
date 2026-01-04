import z from "zod";

export const programSchema = z.object({
  name: z.string().min(1),
  departmentId: z.number()
})
