import { User } from './User';

export interface LoginResponseData {
  accessToken: string;
  expiresIn: number;
  userDTO: User;
}
