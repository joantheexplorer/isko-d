import { useFormContext, Controller } from "react-hook-form";
import Select from "react-select";

type Props = {
  fieldName: string;
  label?: string;
  noLabel?: boolean;
  options: Record<string, any>[];
  isMulti?: boolean;
  placeholder?: string;
};

const SelectGroup = ({
  fieldName,
  label,
  noLabel,
  options,
  isMulti = false,
  placeholder,
}: Props) => {
  const {
    control,
    formState: { errors }
  } = useFormContext();

  const error = (errors as any)?.[fieldName];

  return (
    <div className="flex flex-col mb-5 w-full">
      <label
        className={noLabel ? "hidden" : "block"}
        htmlFor={fieldName}
      >
        {label ?? fieldName}
      </label>

      <Controller
        name={fieldName}
        control={control}
        render={({ field }) => (
          <Select
            {...field}
            options={options}
            isMulti={isMulti}
            placeholder={placeholder ?? label ?? fieldName}
            classNamePrefix="react-select"
            className={
              error ? "react-select-container border-red-500" : ""
            }
            value={
              isMulti
                ? options.filter(opt =>
                    field.value?.includes(opt.value)
                  )
                : options.find(opt => opt.value === field.value) ?? null
            }
            onChange={(selected) => {
              if (isMulti) {
                field.onChange(
                  (selected as Record<string, any>[]).map(opt => opt.value)
                );
              } else {
                field.onChange((selected as Record<string, any>)?.value);
              }
            }}
          />
        )}
      />

      {error && (
        <p className="text-red-600 text-sm mt-1">
          {error.message}
        </p>
      )}
    </div>
  );
};

export default SelectGroup;
