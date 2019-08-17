import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';
import { ContractDocumentEntryRowService } from './contract-document-entry-row.service';
import { ContractDocumentEntryRowComponent } from './contract-document-entry-row.component';
import { ContractDocumentEntryRowDetailComponent } from './contract-document-entry-row-detail.component';
import { ContractDocumentEntryRowUpdateComponent } from './contract-document-entry-row-update.component';
import { ContractDocumentEntryRowDeletePopupComponent } from './contract-document-entry-row-delete-dialog.component';
import { IContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';

@Injectable({ providedIn: 'root' })
export class ContractDocumentEntryRowResolve implements Resolve<IContractDocumentEntryRow> {
  constructor(private service: ContractDocumentEntryRowService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractDocumentEntryRow> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContractDocumentEntryRow>) => response.ok),
        map((contractDocumentEntryRow: HttpResponse<ContractDocumentEntryRow>) => contractDocumentEntryRow.body)
      );
    }
    return of(new ContractDocumentEntryRow());
  }
}

export const contractDocumentEntryRowRoute: Routes = [
  {
    path: '',
    component: ContractDocumentEntryRowComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'remanagementApp.contractDocumentEntryRow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractDocumentEntryRowDetailComponent,
    resolve: {
      contractDocumentEntryRow: ContractDocumentEntryRowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocumentEntryRow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractDocumentEntryRowUpdateComponent,
    resolve: {
      contractDocumentEntryRow: ContractDocumentEntryRowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocumentEntryRow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractDocumentEntryRowUpdateComponent,
    resolve: {
      contractDocumentEntryRow: ContractDocumentEntryRowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocumentEntryRow.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractDocumentEntryRowPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractDocumentEntryRowDeletePopupComponent,
    resolve: {
      contractDocumentEntryRow: ContractDocumentEntryRowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.contractDocumentEntryRow.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
