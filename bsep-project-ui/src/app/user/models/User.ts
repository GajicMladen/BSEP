import { UserRole } from "src/app/shared/enums/UserRole";

export interface User {
  id: number;
  name: string;
  lastName: string;
  email: string;
  password: string;
  pin: string;
  role: UserRole;
   
}
