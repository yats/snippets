import { Moment } from 'moment';

export interface ICategory {
  id?: number;
  categoryCd?: string;
  categoryDesc?: string;
  creationDate?: Moment;
  lastModifiedDate?: Moment;
  actif?: boolean;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public categoryCd?: string,
    public categoryDesc?: string,
    public creationDate?: Moment,
    public lastModifiedDate?: Moment,
    public actif?: boolean
  ) {
    this.actif = this.actif || false;
  }
}
