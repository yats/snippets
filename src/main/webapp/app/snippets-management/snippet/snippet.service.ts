import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISnippet } from 'app/shared/model/snippet.model';

type EntityResponseType = HttpResponse<ISnippet>;
type EntityArrayResponseType = HttpResponse<ISnippet[]>;

@Injectable({ providedIn: 'root' })
export class SnippetService {
  public resourceUrl = SERVER_API_URL + 'api/snippets';

  constructor(protected http: HttpClient) {}

  create(snippet: ISnippet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snippet);
    return this.http
      .post<ISnippet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(snippet: ISnippet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snippet);
    return this.http
      .put<ISnippet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISnippet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISnippet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResults(): string[] {
    return ['Prod', 'Base de données', 'Unix'];
  }

  protected convertDateFromClient(snippet: ISnippet): ISnippet {
    const copy: ISnippet = Object.assign({}, snippet, {
      creationDate: snippet.creationDate && snippet.creationDate.isValid() ? snippet.creationDate.toJSON() : undefined,
      lastModifiedDate: snippet.lastModifiedDate && snippet.lastModifiedDate.isValid() ? snippet.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((snippet: ISnippet) => {
        snippet.creationDate = snippet.creationDate ? moment(snippet.creationDate) : undefined;
        snippet.lastModifiedDate = snippet.lastModifiedDate ? moment(snippet.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
