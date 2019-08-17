import { ITenantContract } from 'app/shared/model/tenant-contract.model';

export interface ITenant {
  id?: number;
  qid?: string;
  name?: string;
  arabicName?: string;
  telephone?: string;
  fax?: string;
  mobile?: string;
  email?: string;
  pobox?: string;
  contracts?: ITenantContract[];
}

export class Tenant implements ITenant {
  constructor(
    public id?: number,
    public qid?: string,
    public name?: string,
    public arabicName?: string,
    public telephone?: string,
    public fax?: string,
    public mobile?: string,
    public email?: string,
    public pobox?: string,
    public contracts?: ITenantContract[]
  ) {}
}
