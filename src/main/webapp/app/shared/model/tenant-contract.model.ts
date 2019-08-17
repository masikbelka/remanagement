import { Moment } from 'moment';
import { IFreePeriod } from 'app/shared/model/free-period.model';
import { IProperty } from 'app/shared/model/property.model';
import { ITenant } from 'app/shared/model/tenant.model';
import { IContractDocument } from 'app/shared/model/contract-document.model';

export interface ITenantContract {
  id?: number;
  code?: string;
  effectiveDate?: Moment;
  startDate?: Moment;
  endDate?: Moment;
  rent?: number;
  deposit?: number;
  utilities?: string;
  freePeriods?: IFreePeriod[];
  properties?: IProperty[];
  tenants?: ITenant[];
  contractDocument?: IContractDocument;
}

export class TenantContract implements ITenantContract {
  constructor(
    public id?: number,
    public code?: string,
    public effectiveDate?: Moment,
    public startDate?: Moment,
    public endDate?: Moment,
    public rent?: number,
    public deposit?: number,
    public utilities?: string,
    public freePeriods?: IFreePeriod[],
    public properties?: IProperty[],
    public tenants?: ITenant[],
    public contractDocument?: IContractDocument
  ) {}
}
