import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFreePeriod } from 'app/shared/model/free-period.model';

type EntityResponseType = HttpResponse<IFreePeriod>;
type EntityArrayResponseType = HttpResponse<IFreePeriod[]>;

@Injectable({ providedIn: 'root' })
export class FreePeriodService {
  public resourceUrl = SERVER_API_URL + 'api/free-periods';

  constructor(protected http: HttpClient) {}

  create(freePeriod: IFreePeriod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(freePeriod);
    return this.http
      .post<IFreePeriod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(freePeriod: IFreePeriod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(freePeriod);
    return this.http
      .put<IFreePeriod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFreePeriod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFreePeriod[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(freePeriod: IFreePeriod): IFreePeriod {
    const copy: IFreePeriod = Object.assign({}, freePeriod, {
      startDate: freePeriod.startDate != null && freePeriod.startDate.isValid() ? freePeriod.startDate.toJSON() : null,
      endDate: freePeriod.endDate != null && freePeriod.endDate.isValid() ? freePeriod.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((freePeriod: IFreePeriod) => {
        freePeriod.startDate = freePeriod.startDate != null ? moment(freePeriod.startDate) : null;
        freePeriod.endDate = freePeriod.endDate != null ? moment(freePeriod.endDate) : null;
      });
    }
    return res;
  }
}
