import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';

type EntityResponseType = HttpResponse<IContractDocumentEntryRow>;
type EntityArrayResponseType = HttpResponse<IContractDocumentEntryRow[]>;

@Injectable({ providedIn: 'root' })
export class ContractDocumentEntryRowService {
  public resourceUrl = SERVER_API_URL + 'api/contract-document-entry-rows';

  constructor(protected http: HttpClient) {}

  create(contractDocumentEntryRow: IContractDocumentEntryRow): Observable<EntityResponseType> {
    return this.http.post<IContractDocumentEntryRow>(this.resourceUrl, contractDocumentEntryRow, { observe: 'response' });
  }

  update(contractDocumentEntryRow: IContractDocumentEntryRow): Observable<EntityResponseType> {
    return this.http.put<IContractDocumentEntryRow>(this.resourceUrl, contractDocumentEntryRow, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContractDocumentEntryRow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContractDocumentEntryRow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
