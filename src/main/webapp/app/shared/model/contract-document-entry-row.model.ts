export interface IContractDocumentEntryRow {
  id?: number;
  position?: number;
  textEn?: string;
  textAr?: string;
}

export class ContractDocumentEntryRow implements IContractDocumentEntryRow {
  constructor(public id?: number, public position?: number, public textEn?: string, public textAr?: string) {}
}
