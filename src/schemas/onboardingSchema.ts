import z from "zod";

export const onboardingSchema = z.object({
  plainToken: z.string().min(36),
})

export type OnboardingInputs = z.infer<typeof onboardingSchema>;
