import { Moment } from 'moment';
import { ICategory } from 'app/shared/model/category.model';
import { ITag } from 'app/shared/model/tag.model';

export interface ISnippet {
  id?: number;
  code?: string;
  libelle?: string;
  description?: string;
  textCode?: string;
  creationDate?: Moment;
  lastModifiedDate?: Moment;
  actif?: boolean;
  gitlabExternalCode?: number;
  categoryCd?: ICategory;
  tagCd?: ITag;
}

export class Snippet implements ISnippet {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public description?: string,
    public textCode?: string,
    public creationDate?: Moment,
    public lastModifiedDate?: Moment,
    public actif?: boolean,
    public gitlabExternalCode?: number,
    public categoryCd?: ICategory,
    public tagCd?: ITag
  ) {
    this.actif = this.actif || false;
  }
}
