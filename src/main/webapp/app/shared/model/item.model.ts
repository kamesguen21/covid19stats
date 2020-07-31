import { ICategory } from 'app/shared/model/category.model';
import { ICdcNews } from 'app/shared/model/cdc-news.model';

export interface IItem {
  id?: string;
  title?: string;
  description?: string;
  link?: string;
  pubDate?: string;
  media?: string;
  categories?: ICategory[];
  cdcNews?: ICdcNews;
}

export class Item implements IItem {
  constructor(
    public id?: string,
    public title?: string,
    public description?: string,
    public link?: string,
    public pubDate?: string,
    public media?: string,
    public categories?: ICategory[],
    public cdcNews?: ICdcNews
  ) {}
}
