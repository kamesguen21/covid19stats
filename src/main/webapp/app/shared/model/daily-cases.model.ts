import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';

export interface IDailyCases {
  id?: string;
  lat?: number;
  lon?: number;
  confirmed?: number;
  active?: number;
  deaths?: number;
  recovered?: number;
  date?: Moment;
  country?: ICountry;
}

export class DailyCases implements IDailyCases {
  constructor(
    public id?: string,
    public lat?: number,
    public lon?: number,
    public confirmed?: number,
    public active?: number,
    public deaths?: number,
    public recovered?: number,
    public date?: Moment,
    public country?: ICountry
  ) {}
}
