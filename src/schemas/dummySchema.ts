import z from "zod";

export const dummySchema = z.object({})

export type dummyInputs = z.infer<typeof dummySchema>;
