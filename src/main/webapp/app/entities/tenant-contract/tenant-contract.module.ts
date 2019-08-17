import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RemanagementSharedModule } from 'app/shared';
import {
  TenantContractComponent,
  TenantContractDetailComponent,
  TenantContractUpdateComponent,
  TenantContractDeletePopupComponent,
  TenantContractDeleteDialogComponent,
  tenantContractRoute,
  tenantContractPopupRoute
} from './';

const ENTITY_STATES = [...tenantContractRoute, ...tenantContractPopupRoute];

@NgModule({
  imports: [RemanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TenantContractComponent,
    TenantContractDetailComponent,
    TenantContractUpdateComponent,
    TenantContractDeleteDialogComponent,
    TenantContractDeletePopupComponent
  ],
  entryComponents: [
    TenantContractComponent,
    TenantContractUpdateComponent,
    TenantContractDeleteDialogComponent,
    TenantContractDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementTenantContractModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
