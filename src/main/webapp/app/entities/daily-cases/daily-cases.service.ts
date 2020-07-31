import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDailyCases } from 'app/shared/model/daily-cases.model';

type EntityResponseType = HttpResponse<IDailyCases>;
type EntityArrayResponseType = HttpResponse<IDailyCases[]>;

@Injectable({ providedIn: 'root' })
export class DailyCasesService {
  public resourceUrl = SERVER_API_URL + 'api/daily-cases';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/daily-cases';

  constructor(protected http: HttpClient) {}

  create(dailyCases: IDailyCases): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyCases);
    return this.http
      .post<IDailyCases>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyCases: IDailyCases): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyCases);
    return this.http
      .put<IDailyCases>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDailyCases>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyCases[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyCases[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dailyCases: IDailyCases): IDailyCases {
    const copy: IDailyCases = Object.assign({}, dailyCases, {
      date: dailyCases.date && dailyCases.date.isValid() ? dailyCases.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dailyCases: IDailyCases) => {
        dailyCases.date = dailyCases.date ? moment(dailyCases.date) : undefined;
      });
    }
    return res;
  }
}
