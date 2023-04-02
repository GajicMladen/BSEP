import { RequestStatus } from 'src/app/shared/enums/RequestStatus';

export interface Csr {
  commonName: string;
  organizationName: string;
  organizationalUnit: string;
  locality: string;
  state: string;
  country: string;
  email: string;
  algorithm: string;
  status?: RequestStatus;
}
