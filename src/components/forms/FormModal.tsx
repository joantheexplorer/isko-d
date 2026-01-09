import { ReactNode } from "react";

type Props = {
  isOpen: boolean;
  handleClose: () => void;
  title: string;
  formInputs: ReactNode;
}

const FormModal = ({ isOpen, title, handleClose, formInputs }: Props) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div
        className="w-full max-w-lg rounded-lg bg-white shadow-lg"
      >
        <div className="flex items-center justify-between px-5 py-3">
          <h2 className="text-lg font-semibold">{title}</h2>
        </div>

        <div className="px-5 py-4">{formInputs}</div>

        <div className="flex flex-row-reverse px-5 py-3 gap-2">
          <button
            onClick={() => {
              const form = document.getElementById("modal-form") as HTMLFormElement | null;
              form?.requestSubmit();
            }}
            className="bg-red-700 rounded-sm text-white hover:bg-red-600 p-2 transition-colors"
          >
            Submit
          </button>
          <button
            onClick={handleClose}
            className="bg-gray-800/50 rounded-sm text-white hover:bg-gray-700/50 p-2 transition-colors"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
}

export default FormModal;
