import { IDailyCases } from 'app/shared/model/daily-cases.model';

export interface ICountry {
  id?: string;
  country?: string;
  slug?: string;
  iSO2?: string;
  dailyCases?: IDailyCases[];
}

export class Country implements ICountry {
  constructor(public id?: string, public country?: string, public slug?: string, public iSO2?: string, public dailyCases?: IDailyCases[]) {}
}
