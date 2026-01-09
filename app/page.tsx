"use client";

import SelectGroup from "@/src/components/forms/SelectGroup";
import { KioskInputs, kioskSchema } from "@/src/schemas/kioskSchema";
import { ApiError } from "@/src/types/ApiError";
import apiFetch from "@/src/utils/apiFetch";
import toSelectOptions from "@/src/utils/toSelectOptions";
import { zodResolver } from "@hookform/resolvers/zod";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useEffect, useRef, useState } from "react";
import { BarcodeScanner, DetectedBarcode } from "react-barcode-scanner";
import "react-barcode-scanner/polyfill";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";

const KioskPage = () => {
  const methods = useForm<KioskInputs>({
    resolver: zodResolver(kioskSchema)
  });

  const router = useRouter();
  const [isScanning, setIsScanning] = useState(true);
  const [actions, setActions] = useState<Record<string, any>[]>([]);
  const [responseStatus, setResponseStatus] = useState("");
  const [responseMessage, setResponseMessage] = useState<React.ReactNode>(null);

  useEffect(() => {
    const deviceToken = localStorage.getItem("device_token");
    if (deviceToken == null) router.push("onboarding")
  }, [])
  
  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const actionsRaw = await apiFetch("actions", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("device_token")}`
          },
          credentials: "omit"
        });
        setActions(toSelectOptions(actionsRaw, "id", "name"));
      } catch (error) {
        if (error instanceof ApiError) {
          if (error.status === 401) {
            localStorage.removeItem("device_token");
            localStorage.removeItem("device_id");
            router.push("/onboarding");
          }
        }
      }
    }
    fetchLocations();
  }, [])

  const onSubmit: SubmitHandler<KioskInputs> = async (data) => {
    try {
      const res = await apiFetch("kiosk/log", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("device_token")}`
        },
        method: "POST",
        body: data,
        credentials: "omit"
      });

      setResponseStatus("Recorded Successfully");
      setResponseMessage(
        <p>
          Your record has been successfully submitted. 
          <br /> 
          Thank you, {res.user?.firstName}!
        </p>)
    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 401) {
          localStorage.removeItem("device_token");
          localStorage.removeItem("device_id");
          router.push("/onboarding");
        } else if (error.status === 404) {
          setResponseStatus("Your ID is Not Registered!");
          setResponseMessage(
            <p>
              ID {methods.getValues("barcode")} is not registered.
              <br />
              <br />
              Please visit the Isko'D registration page to register your ID
              <br />
              or ask assistance from an administrator.
            </p>
          )
        }
      } else {
        console.error(error);
      }
    }
  }

  const handleCapture = (detectedBarcodes: DetectedBarcode[]) => {
    if (isScanning && methods.getValues("actionId") !== undefined) {
      setIsScanning(false);
      methods.setValue("barcode", detectedBarcodes[0].rawValue, { shouldValidate: true });
      methods.setValue("deviceId", Number(localStorage.getItem("device_id")), { shouldValidate: true });
      methods.handleSubmit(onSubmit)();
    }
  }

  const handleClose = () => {
    setResponseStatus("");
    setResponseMessage("");
    setIsScanning(true);
    methods.reset();
  }

  return (
    <div className="relative flex min-h-screen border-black font-sans">
      <button
        onClick={() => router.push("/register")}
        className="absolute top-4 left-4 font-extrabold text-red-700 m-4 rounded-md hover:text-red-600 cursor-pointer transition-colors z-20"
      >
        Don't have an account? Register here!
      </button>
      <Image className="object-cover" src="/assets/login.jpg" alt="Library" fill />
      <div className="bg-gray-300/50 backdrop-blur-lg z-10 flex flex-col justify-center items-center p-8 m-4 rounded-lg w-full">
        <Image src="/assets/logo.png" alt="Logo" width={128} height={128} />
        <h1 className="text-3xl text-center mt-5">
          <strong>Isko'D</strong>: One ID, All Access
        </h1>
        <p className="text-center">
          <strong>PUP: University Library and Learning Resources Center</strong>
        </p>
        <FormProvider {...methods}>
          <form className="w-full md:w-1/2 lg:w-1/3 mt-5" onSubmit={methods.handleSubmit(onSubmit)}>
            <SelectGroup fieldName="actionId" label="Action" options={actions} noLabel={true} />
            <div className="w-full">
              <BarcodeScanner 
                onCapture={handleCapture}
                options={{ 
                  delay: 1000,
                  formats: ['code_39'] 
                }}
              />
            </div>
          </form>
        </FormProvider>
      </div>

      {/* Response Modal */}
      { responseStatus && responseMessage &&
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
          <div
            className="w-full max-w-lg rounded-lg bg-white shadow-lg"
          >
            <div className="flex items-center justify-between px-5 py-3">
              <h2 className="text-lg font-semibold">{responseStatus}</h2>
            </div>

            <div className="px-5 py-4">{responseMessage}</div>

            <div className="flex flex-row-reverse px-5 py-3 gap-2">
              <button
                onClick={handleClose}
                className="bg-red-700 rounded-sm text-white hover:bg-red-600 p-2"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      }
    </div>
  );
}

export default KioskPage;
