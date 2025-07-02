import React from "react";

type FormInputProps = React.InputHTMLAttributes<HTMLInputElement>;

const FormInput = React.forwardRef<HTMLInputElement, FormInputProps>(
  ({ type, name, placeholder, ...rest }, ref) => {
    return (
      <input
        ref={ref}
        type={type}
        name={name}
        placeholder={placeholder}
        className="form-input"
        {...rest}
      />
    );
  }
);

export default FormInput;
