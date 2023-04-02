import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const countryCodeValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const value = control.value;
  const uppercaseLatinRegex = /^[A-Z]+$/;

  if (value.length !== 2) return { invalidLengthError: true };
  if (!uppercaseLatinRegex.test(value)) return { invalidCharactersError: true };

  return null;
};

export const emailValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const value = control.value;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!emailRegex.test(value)) return { invalidEmailFormat: true };
  return null;
};
