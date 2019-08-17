import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { TenantComponent } from './tenant.component';
import { TenantDetailComponent } from './tenant-detail.component';
import { TenantUpdateComponent } from './tenant-update.component';
import { TenantDeletePopupComponent } from './tenant-delete-dialog.component';
import { ITenant } from 'app/shared/model/tenant.model';

@Injectable({ providedIn: 'root' })
export class TenantResolve implements Resolve<ITenant> {
  constructor(private service: TenantService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITenant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tenant>) => response.ok),
        map((tenant: HttpResponse<Tenant>) => tenant.body)
      );
    }
    return of(new Tenant());
  }
}

export const tenantRoute: Routes = [
  {
    path: '',
    component: TenantComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'remanagementApp.tenant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TenantDetailComponent,
    resolve: {
      tenant: TenantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TenantUpdateComponent,
    resolve: {
      tenant: TenantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TenantUpdateComponent,
    resolve: {
      tenant: TenantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tenantPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TenantDeletePopupComponent,
    resolve: {
      tenant: TenantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.tenant.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
