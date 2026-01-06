import z from "zod";

export const kioskSchema = z.object({
  barcode: z.string().min(15),
  deviceId: z.number(),
  actionId: z.number()
})

export type KioskInputs = z.infer<typeof kioskSchema>;
