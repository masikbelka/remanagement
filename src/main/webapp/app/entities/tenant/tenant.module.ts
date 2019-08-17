import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RemanagementSharedModule } from 'app/shared';
import {
  TenantComponent,
  TenantDetailComponent,
  TenantUpdateComponent,
  TenantDeletePopupComponent,
  TenantDeleteDialogComponent,
  tenantRoute,
  tenantPopupRoute
} from './';

const ENTITY_STATES = [...tenantRoute, ...tenantPopupRoute];

@NgModule({
  imports: [RemanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TenantComponent, TenantDetailComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
  entryComponents: [TenantComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementTenantModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
