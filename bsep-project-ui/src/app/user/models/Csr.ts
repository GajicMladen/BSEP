import { RequestStatus } from 'src/app/shared/enums/RequestStatus';

export interface Csr {
  id: number;
  commonName: string;
  organizationName: string;
  organizationalUnit: string;
  locality: string;
  state: string;
  country: string;
  email: string;
  algorithm: string;
  status?: RequestStatus;
  startDate: Date;
  endDate: Date;
  version: number;
  alias: string;
  intermediateCertificate: string;
}
