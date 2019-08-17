import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from './tenant-contract.service';
import { TenantContractComponent } from './tenant-contract.component';
import { TenantContractDetailComponent } from './tenant-contract-detail.component';
import { TenantContractUpdateComponent } from './tenant-contract-update.component';
import { TenantContractDeletePopupComponent } from './tenant-contract-delete-dialog.component';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';

@Injectable({ providedIn: 'root' })
export class TenantContractResolve implements Resolve<ITenantContract> {
  constructor(private service: TenantContractService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITenantContract> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TenantContract>) => response.ok),
        map((tenantContract: HttpResponse<TenantContract>) => tenantContract.body)
      );
    }
    return of(new TenantContract());
  }
}

export const tenantContractRoute: Routes = [
  {
    path: '',
    component: TenantContractComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'remanagementApp.tenantContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TenantContractDetailComponent,
    resolve: {
      tenantContract: TenantContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenantContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TenantContractUpdateComponent,
    resolve: {
      tenantContract: TenantContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenantContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TenantContractUpdateComponent,
    resolve: {
      tenantContract: TenantContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenantContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tenantContractPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TenantContractDeletePopupComponent,
    resolve: {
      tenantContract: TenantContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenantContract.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
