import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';

type EntityResponseType = HttpResponse<ITenantContract>;
type EntityArrayResponseType = HttpResponse<ITenantContract[]>;

@Injectable({ providedIn: 'root' })
export class TenantContractService {
  public resourceUrl = SERVER_API_URL + 'api/tenant-contracts';

  constructor(protected http: HttpClient) {}

  create(tenantContract: ITenantContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tenantContract);
    return this.http
      .post<ITenantContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tenantContract: ITenantContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tenantContract);
    return this.http
      .put<ITenantContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITenantContract>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITenantContract[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tenantContract: ITenantContract): ITenantContract {
    const copy: ITenantContract = Object.assign({}, tenantContract, {
      effectiveDate:
        tenantContract.effectiveDate != null && tenantContract.effectiveDate.isValid() ? tenantContract.effectiveDate.toJSON() : null,
      startDate: tenantContract.startDate != null && tenantContract.startDate.isValid() ? tenantContract.startDate.toJSON() : null,
      endDate: tenantContract.endDate != null && tenantContract.endDate.isValid() ? tenantContract.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.effectiveDate = res.body.effectiveDate != null ? moment(res.body.effectiveDate) : null;
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tenantContract: ITenantContract) => {
        tenantContract.effectiveDate = tenantContract.effectiveDate != null ? moment(tenantContract.effectiveDate) : null;
        tenantContract.startDate = tenantContract.startDate != null ? moment(tenantContract.startDate) : null;
        tenantContract.endDate = tenantContract.endDate != null ? moment(tenantContract.endDate) : null;
      });
    }
    return res;
  }
}
