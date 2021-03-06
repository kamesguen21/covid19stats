import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IGlobalConfigurations } from 'app/shared/model/global-configurations.model';

type EntityResponseType = HttpResponse<IGlobalConfigurations>;
type EntityArrayResponseType = HttpResponse<IGlobalConfigurations[]>;

@Injectable({ providedIn: 'root' })
export class GlobalConfigurationsService {
  public resourceUrl = SERVER_API_URL + 'api/global-configurations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/global-configurations';

  constructor(protected http: HttpClient) {}

  create(globalConfigurations: IGlobalConfigurations): Observable<EntityResponseType> {
    return this.http.post<IGlobalConfigurations>(this.resourceUrl, globalConfigurations, { observe: 'response' });
  }

  update(globalConfigurations: IGlobalConfigurations): Observable<EntityResponseType> {
    return this.http.put<IGlobalConfigurations>(this.resourceUrl, globalConfigurations, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGlobalConfigurations>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGlobalConfigurations[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGlobalConfigurations[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
