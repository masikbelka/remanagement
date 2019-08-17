import { Moment } from 'moment';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';

export interface IFreePeriod {
  id?: number;
  code?: string;
  startDate?: Moment;
  endDate?: Moment;
  contracts?: ITenantContract[];
}

export class FreePeriod implements IFreePeriod {
  constructor(
    public id?: number,
    public code?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public contracts?: ITenantContract[]
  ) {}
}
