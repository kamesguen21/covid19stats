import { IItem } from 'app/shared/model/item.model';

export interface ICdcNews {
  id?: string;
  title?: string;
  description?: string;
  pubDate?: string;
  lastBuildDate?: string;
  link?: string;
  items?: IItem[];
}

export class CdcNews implements ICdcNews {
  constructor(
    public id?: string,
    public title?: string,
    public description?: string,
    public pubDate?: string,
    public lastBuildDate?: string,
    public link?: string,
    public items?: IItem[]
  ) {}
}
