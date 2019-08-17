import { ILocation } from 'app/shared/model/location.model';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';

export const enum UnitType {
  VILLA = 'VILLA',
  APPARTMENT = 'APPARTMENT',
  OFFICE_SPACE = 'OFFICE_SPACE',
  SHOWROOM = 'SHOWROOM'
}

export const enum Furnishing {
  FURNISHED = 'FURNISHED',
  UNFURNISHED = 'UNFURNISHED'
}

export interface IProperty {
  id?: number;
  code?: string;
  name?: string;
  number?: string;
  type?: UnitType;
  bedrooms?: number;
  furnishing?: Furnishing;
  electricity?: number;
  water?: number;
  parking?: number;
  location?: ILocation;
  contracts?: ITenantContract[];
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public number?: string,
    public type?: UnitType,
    public bedrooms?: number,
    public furnishing?: Furnishing,
    public electricity?: number,
    public water?: number,
    public parking?: number,
    public location?: ILocation,
    public contracts?: ITenantContract[]
  ) {}
}
