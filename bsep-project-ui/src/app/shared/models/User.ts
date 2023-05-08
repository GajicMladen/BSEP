import { Role } from './Role';

export interface User {
  email: string;
  firstName: string;
  lastName: string;
  roles: Role[];
}
