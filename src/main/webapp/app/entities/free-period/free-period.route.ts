import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FreePeriod } from 'app/shared/model/free-period.model';
import { FreePeriodService } from './free-period.service';
import { FreePeriodComponent } from './free-period.component';
import { FreePeriodDetailComponent } from './free-period-detail.component';
import { FreePeriodUpdateComponent } from './free-period-update.component';
import { FreePeriodDeletePopupComponent } from './free-period-delete-dialog.component';
import { IFreePeriod } from 'app/shared/model/free-period.model';

@Injectable({ providedIn: 'root' })
export class FreePeriodResolve implements Resolve<IFreePeriod> {
  constructor(private service: FreePeriodService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFreePeriod> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FreePeriod>) => response.ok),
        map((freePeriod: HttpResponse<FreePeriod>) => freePeriod.body)
      );
    }
    return of(new FreePeriod());
  }
}

export const freePeriodRoute: Routes = [
  {
    path: '',
    component: FreePeriodComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'remanagementApp.freePeriod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FreePeriodDetailComponent,
    resolve: {
      freePeriod: FreePeriodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.freePeriod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FreePeriodUpdateComponent,
    resolve: {
      freePeriod: FreePeriodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.freePeriod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FreePeriodUpdateComponent,
    resolve: {
      freePeriod: FreePeriodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.freePeriod.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const freePeriodPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FreePeriodDeletePopupComponent,
    resolve: {
      freePeriod: FreePeriodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'remanagementApp.freePeriod.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
