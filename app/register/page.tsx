"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import { RegisterInputs, registerSchema } from "@/src/schemas/registerSchema";
import { ApiError } from "@/src/types/ApiError";
import apiFetch from "@/src/utils/apiFetch";
import { zodResolver } from "@hookform/resolvers/zod";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";

const RegisterPage = () => {
  const [responseStatus, setResponseStatus] = useState("");
  const [responseMessage, setResponseMessage] = useState<React.ReactNode>(null);

  const router = useRouter();
  const methods = useForm<RegisterInputs>({
    resolver: zodResolver(registerSchema)
  });

  const onSubmit: SubmitHandler<RegisterInputs> = async (data) => {
    console.log(data);
    try {
      const res = await apiFetch("auth/register", {
        method: "POST",
        body: data
      });

      setResponseStatus("Registered Successfully");
      setResponseMessage(<p>Your account has been registered successfully. <br /> You may now authenticate through the kiosk using your Student ID.</p>);
    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 500 && error.message.startsWith("could not execute statement") && error.message.includes("barcode")) {
          setResponseStatus("Student ID is Already Registered");
          setResponseMessage(
            <p>
              The Student ID {methods.getValues("barcode")} is already registered.
              <br />
              If you think this is a mistake, please contact an administrator.
            </p>
          );
        } else if (error.status === 500 && error.message.startsWith("could not execute statement") && error.message.includes("email")) {
          setResponseStatus("Email is Already Registered");
          setResponseMessage(
            <p>
              The Email {methods.getValues("email")} is already registered.
              <br />
              If you think this is a mistake, please contact an administrator.
            </p>
          );
        }
      }
      console.error(error);
    }
  }

  const handleClose = () => {
    setResponseStatus("");
    setResponseMessage("");
    methods.reset();
  }

  return (
    <div className="relative flex min-h-screen border-black font-sans">
      <button
        onClick={() => router.push("/")} // replace with your kiosk route
        className="absolute top-4 left-4 font-extrabold text-red-700 m-4 rounded-md hover:text-red-600 cursor-pointer transition-colors z-20"
      >
        ← Back to Kiosk
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
            <InputGroup fieldName="barcode" label="Student ID" noLabel={true} />
            <InputGroup fieldName="firstName" label="First Name" noLabel={true} />
            <InputGroup fieldName="middleName" label="Middle Name" noLabel={true} />
            <InputGroup fieldName="lastName" label="Last Name" noLabel={true} />
            <InputGroup fieldName="email" label="Email" noLabel={true} type="email" />
            <InputGroup fieldName="password" label="Password" noLabel={true} type="password" />
            <button className="bg-red-900 text-center text-white p-2 rounded-md w-full cursor-pointer hover:bg-red-800 transition-colors">
              <strong>Submit</strong>
            </button>
          </form>
        </FormProvider>
      </div>

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

export default RegisterPage;
