import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractDocument } from 'app/shared/model/contract-document.model';

type EntityResponseType = HttpResponse<IContractDocument>;
type EntityArrayResponseType = HttpResponse<IContractDocument[]>;

@Injectable({ providedIn: 'root' })
export class ContractDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/contract-documents';

  constructor(protected http: HttpClient) {}

  create(contractDocument: IContractDocument): Observable<EntityResponseType> {
    return this.http.post<IContractDocument>(this.resourceUrl, contractDocument, { observe: 'response' });
  }

  update(contractDocument: IContractDocument): Observable<EntityResponseType> {
    return this.http.put<IContractDocument>(this.resourceUrl, contractDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContractDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContractDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
