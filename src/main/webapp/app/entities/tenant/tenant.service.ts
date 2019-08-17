import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITenant } from 'app/shared/model/tenant.model';

type EntityResponseType = HttpResponse<ITenant>;
type EntityArrayResponseType = HttpResponse<ITenant[]>;

@Injectable({ providedIn: 'root' })
export class TenantService {
  public resourceUrl = SERVER_API_URL + 'api/tenants';

  constructor(protected http: HttpClient) {}

  create(tenant: ITenant): Observable<EntityResponseType> {
    return this.http.post<ITenant>(this.resourceUrl, tenant, { observe: 'response' });
  }

  update(tenant: ITenant): Observable<EntityResponseType> {
    return this.http.put<ITenant>(this.resourceUrl, tenant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITenant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITenant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
