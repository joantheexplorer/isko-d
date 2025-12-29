"use client";

import InputGroup from "@/src/components/forms/InputGroup";
import { LoginInputs, loginSchema } from "@/src/schemas/loginSchema";
import { ApiError } from "@/src/types/ApiError";
import apiFetch from "@/src/utils/apiFetch";
import { zodResolver } from "@hookform/resolvers/zod";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";

const LoginPage = () => {
  const router = useRouter();
  const methods = useForm<LoginInputs>({
    resolver: zodResolver(loginSchema)
  });

  const rootError = methods.formState.errors?.root;

  const onSubmit: SubmitHandler<LoginInputs> = async (data) => {
    try {
      const res = await apiFetch("auth/login", {
        method: "POST",
        body: data
      });

      router.push("/admin7vsuo5zd");

    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 401) {
          methods.setError("root", {
            message: "Invalid credentials"
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
      <div className="bg-gray-300/50 backdrop-blur-lg z-10 ml-auto flex flex-col justify-center items-center p-8">
        <Image src="/assets/logo.png" alt="Logo" width={128} height={128} />
        <h1 className="text-3xl text-center mt-5">
          <strong>Isko'D</strong>: One ID, All Access
        </h1>
        <p className="text-center">
          <strong>PUP: University Library and Learning Resources Center</strong>
        </p>
        <FormProvider {...methods}>
          <form className="w-full mt-5" onSubmit={methods.handleSubmit(onSubmit)}>
            <p className="text-red-600">{rootError?.message}</p>
            <InputGroup fieldName="email" label="Email" noLabel={true} type="email" />
            <InputGroup fieldName="password" label="Password" noLabel={true} type="password" />
            <button className="bg-red-900 text-center text-white p-2 rounded-md w-full cursor-pointer hover:bg-red-800 transition-colors">
              <strong>Submit</strong>
            </button>
          </form>
        </FormProvider>
      </div>
    </div>
  );
}

export default LoginPage;
