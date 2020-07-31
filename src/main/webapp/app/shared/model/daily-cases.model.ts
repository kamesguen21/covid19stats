import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IDailyCases {
  id?: string;
  country?: string;
  countryCode?: string;
  province?: string;
  city?: string;
  cityCode?: string;
  lat?: number;
  lon?: number;
  cases?: number;
  status?: Status;
  date?: Moment;
}

export class DailyCases implements IDailyCases {
  constructor(
    public id?: string,
    public country?: string,
    public countryCode?: string,
    public province?: string,
    public city?: string,
    public cityCode?: string,
    public lat?: number,
    public lon?: number,
    public cases?: number,
    public status?: Status,
    public date?: Moment
  ) {}
}
