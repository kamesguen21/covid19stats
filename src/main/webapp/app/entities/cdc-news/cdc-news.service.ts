import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICdcNews } from 'app/shared/model/cdc-news.model';

type EntityResponseType = HttpResponse<ICdcNews>;
type EntityArrayResponseType = HttpResponse<ICdcNews[]>;

@Injectable({ providedIn: 'root' })
export class CdcNewsService {
  public resourceUrl = SERVER_API_URL + 'api/cdc-news';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cdc-news';

  constructor(protected http: HttpClient) {}

  create(cdcNews: ICdcNews): Observable<EntityResponseType> {
    return this.http.post<ICdcNews>(this.resourceUrl, cdcNews, { observe: 'response' });
  }

  update(cdcNews: ICdcNews): Observable<EntityResponseType> {
    return this.http.put<ICdcNews>(this.resourceUrl, cdcNews, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICdcNews>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICdcNews[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICdcNews[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
