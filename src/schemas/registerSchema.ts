import z from "zod";

export const registerSchema = z.object({
  barcode: z.string().min(15, "Student ID is required"),
  firstName: z.string().min(1, "First Name is required"),
  middleName: z.string().nullish(),
  lastName: z.string().min(1, "Last Name is required"),
  email: z.email().min(1, "Email is required"),
  password: z.string().min(1, "Password is required")
})

export type RegisterInputs = z.infer<typeof registerSchema>;
