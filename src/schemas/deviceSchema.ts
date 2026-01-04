import z from "zod";

export const deviceSchema = z.object({
  name: z.string().min(1),
  locationId: z.number()
})
