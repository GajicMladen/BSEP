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

export const passwordCharactersValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const value: string = control.value;

  if (!value) return null;

  const CHAR_LOWER = 'abcdefghijklmnopqrstuvwxyz';
  const CHAR_UPPER = CHAR_LOWER.toUpperCase();
  const NUMBER = '0123456789';
  const SPECIAL_CHARS = '!@#$%^&*_=+-/';

  if (!includesAny(value, CHAR_LOWER))
    return {
      lowerMissingError: 'At least one lowercase character is required!',
    };

  if (!includesAny(value, CHAR_UPPER))
    return {
      upperMissingError: 'At least one uppercase character is required!',
    };

  if (!includesAny(value, NUMBER))
    return { numberMissingError: 'At least one number is required!' };

  if (!includesAny(value, SPECIAL_CHARS))
    return {
      specialMissingError:
        'At least one of the following is required: ' + SPECIAL_CHARS + ' !',
    };

  return null;
};

function includesAny(targetString: string, alphabet: string): boolean {
  for (let char of targetString) {
    if (alphabet.includes(char)) return true;
    else continue;
  }
  return false;
}
