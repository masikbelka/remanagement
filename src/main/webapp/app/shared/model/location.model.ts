export interface ILocation {
  id?: number;
  city?: string;
  building?: string;
  zone?: string;
  street?: string;
}

export class Location implements ILocation {
  constructor(public id?: number, public city?: string, public building?: string, public zone?: string, public street?: string) {}
}
