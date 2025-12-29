import { useFormContext } from "react-hook-form";

type Props = {
  fieldName: string,
  label?: string,
  noLabel?: boolean,
  type?: string,
  rest?: Record<string, any>
}

const FormGroup = ({ fieldName, label, noLabel, type, rest }: Props ) => {
  const {
    register,
    formState: { errors }
  } = useFormContext();

  const error = (errors as any)?.[fieldName];

  return (
    <div className="flex flex-col mb-5 w-full">
      <label className={noLabel ? "hidden" : "block"} htmlFor={fieldName}>{label ?? fieldName}</label>
      <input className={`bg-white w-full rounded-md p-1 ${!!error || !!errors?.root ? "border-1 border-red-500 text-red-600" : ""}`}
        {...register(fieldName)}
        type={type ?? "text"}
        placeholder={label ?? fieldName}
        {...rest}
      />
      <p className="text-red-600">{error?.message}</p>
    </div>
  );
}

export default FormGroup;
