import { useFormContext } from "react-hook-form";

type Props = {
  fieldName: string,
  label?: string,
  noLabel?: boolean,
  type?: string,
  required?: boolean,
  rest?: Record<string, any>
}

const InputGroup = ({ fieldName, label, noLabel, type, required, rest }: Props ) => {
  const {
    register,
    formState: { errors }
  } = useFormContext();

  const error = (errors as any)?.[fieldName];

  return (
    <div className="flex flex-col mb-5 w-full">
      <label className={noLabel ? "hidden" : "block"} htmlFor={fieldName}>
        {label ?? fieldName}
        { required &&
          <span className="text-red-600"> *</span>
        }
      </label>
      <input className={`border border-gray-300 rounded px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-1 focus:ring-red-500 focus:border-red-500 w-full hover:ring-red-300 hover:border-red-300 transition-colors bg-white ${!!error || !!errors?.root ? "border-1 border-red-500 text-red-600" : ""}`}
        {...register(fieldName)}
        type={type ?? "text"}
        placeholder={label ?? fieldName}
        required={required}
        {...rest}
      />
      <p className="text-red-600">{error?.message}</p>
    </div>
  );
}

export default InputGroup;
