import z from "zod";

export const actionSchema = z.object({
  name: z.string().min(1)
})

export type ActionInputs = z.infer<typeof actionSchema>;
