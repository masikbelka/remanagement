import { ITenantContract } from 'app/shared/model/tenant-contract.model';

export const enum ContractDocumentType {
  LEASE = 'LEASE',
  RENT = 'RENT',
  SELL = 'SELL'
}

export interface IContractDocument {
  id?: number;
  code?: string;
  type?: ContractDocumentType;
  fileName?: string;
  tenantContacts?: ITenantContract[];
}

export class ContractDocument implements IContractDocument {
  constructor(
    public id?: number,
    public code?: string,
    public type?: ContractDocumentType,
    public fileName?: string,
    public tenantContacts?: ITenantContract[]
  ) {}
}
