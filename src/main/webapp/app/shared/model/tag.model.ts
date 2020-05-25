import { Moment } from 'moment';

export interface ITag {
  id?: number;
  tagCd?: string;
  tagDesc?: string;
  creationDate?: Moment;
  lastModifiedDate?: Moment;
  actif?: boolean;
}

export class Tag implements ITag {
  constructor(
    public id?: number,
    public tagCd?: string,
    public tagDesc?: string,
    public creationDate?: Moment,
    public lastModifiedDate?: Moment,
    public actif?: boolean
  ) {
    this.actif = this.actif || false;
  }
}
