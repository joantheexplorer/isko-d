import z from "zod";

export const userSchema = z.object({
  barcode: z.string().nullish(),
  firstName: z.string().min(1),
  middleName: z.string().nullish(),
  lastName: z.string().min(1),
  email: z.email(),
  password: z.string().min(1),
  roleId: z.number()
})
