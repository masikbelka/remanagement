import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractDocument } from 'app/shared/model/contract-document.model';
import { ContractDocumentService } from './contract-document.service';
import { ContractDocumentComponent } from './contract-document.component';
import { ContractDocumentDetailComponent } from './contract-document-detail.component';
import { ContractDocumentUpdateComponent } from './contract-document-update.component';
import { ContractDocumentDeletePopupComponent } from './contract-document-delete-dialog.component';
import { IContractDocument } from 'app/shared/model/contract-document.model';

@Injectable({ providedIn: 'root' })
export class ContractDocumentResolve implements Resolve<IContractDocument> {
  constructor(private service: ContractDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractDocument> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContractDocument>) => response.ok),
        map((contractDocument: HttpResponse<ContractDocument>) => contractDocument.body)
      );
    }
    return of(new ContractDocument());
  }
}

export const contractDocumentRoute: Routes = [
  {
    path: '',
    component: ContractDocumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'remanagementApp.contractDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractDocumentDetailComponent,
    resolve: {
      contractDocument: ContractDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractDocumentUpdateComponent,
    resolve: {
      contractDocument: ContractDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractDocumentUpdateComponent,
    resolve: {
      contractDocument: ContractDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractDocumentDeletePopupComponent,
    resolve: {
      contractDocument: ContractDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
