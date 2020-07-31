import { IItem } from 'app/shared/model/item.model';

export interface ICategory {
  id?: string;
  name?: string;
  item?: IItem;
}

export class Category implements ICategory {
  constructor(public id?: string, public name?: string, public item?: IItem) {}
}
