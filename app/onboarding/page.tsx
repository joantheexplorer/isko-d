"use client";

import Image from "next/image";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { onboardingSchema, OnboardingInputs } from "@/src/schemas/onboardingSchema";
import apiFetch from "@/src/utils/apiFetch";
import { ApiError } from "@/src/types/ApiError";
import InputGroup from "@/src/components/forms/InputGroup";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

const KioskOnboardingPage = () => {
  const router = useRouter();

  useEffect(() => {
    const deviceToken = localStorage.getItem("device_token");
    if (deviceToken) router.push("/");
  }, []);

  const methods = useForm<OnboardingInputs>({
    resolver: zodResolver(onboardingSchema)
  });

  const rootError = methods.formState.errors?.root;

  const onSubmit: SubmitHandler<OnboardingInputs> = async (data) => {
    try {
      const res = await apiFetch("kiosk/validate", {
        headers: {
          Authorization: `Bearer ${data.plainToken}`
        },
        method: "POST",
        body: data,
        credentials: "omit"
      });

      localStorage.setItem("device_token", data.plainToken);
      localStorage.setItem("device_id", res.id);

      router.push("/");
    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 401) {
          methods.setError("root", {
            message: "Invalid token. Please contact your administrator."
          })
        }
      } else {
        console.error(error);
      }
    }
  }

  return (
    <div className="relative flex min-h-screen border-black font-sans">
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
          <p className="mt-4">Enter a valid API token provided by your local administrator.</p>
          <form className="w-full md:w-1/2 lg:w-1/3 mt-5" onSubmit={methods.handleSubmit(onSubmit)}>
            <p className="text-red-600">{rootError?.message}</p>
            <InputGroup fieldName="plainToken" label="Token" noLabel={true} />
            <button className="bg-red-900 text-center text-white p-2 rounded-md w-full cursor-pointer hover:bg-red-800 transition-colors">
              <strong>Submit</strong>
            </button>
          </form>
        </FormProvider>
      </div>
    </div>
  );
}

export default KioskOnboardingPage;
