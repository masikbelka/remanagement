import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RemanagementSharedModule } from 'app/shared';
import {
  ContractDocumentComponent,
  ContractDocumentDetailComponent,
  ContractDocumentUpdateComponent,
  ContractDocumentDeletePopupComponent,
  ContractDocumentDeleteDialogComponent,
  contractDocumentRoute,
  contractDocumentPopupRoute
} from './';

const ENTITY_STATES = [...contractDocumentRoute, ...contractDocumentPopupRoute];

@NgModule({
  imports: [RemanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractDocumentComponent,
    ContractDocumentDetailComponent,
    ContractDocumentUpdateComponent,
    ContractDocumentDeleteDialogComponent,
    ContractDocumentDeletePopupComponent
  ],
  entryComponents: [
    ContractDocumentComponent,
    ContractDocumentUpdateComponent,
    ContractDocumentDeleteDialogComponent,
    ContractDocumentDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementContractDocumentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
