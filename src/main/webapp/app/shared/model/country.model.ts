export interface ICountry {
  id?: string;
  country?: string;
  slug?: string;
  iSO2?: string;
}

export class Country implements ICountry {
  constructor(public id?: string, public country?: string, public slug?: string, public iSO2?: string) {}
}
