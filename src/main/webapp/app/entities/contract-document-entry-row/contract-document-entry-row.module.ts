import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RemanagementSharedModule } from 'app/shared';
import {
  ContractDocumentEntryRowComponent,
  ContractDocumentEntryRowDetailComponent,
  ContractDocumentEntryRowUpdateComponent,
  ContractDocumentEntryRowDeletePopupComponent,
  ContractDocumentEntryRowDeleteDialogComponent,
  contractDocumentEntryRowRoute,
  contractDocumentEntryRowPopupRoute
} from './';

const ENTITY_STATES = [...contractDocumentEntryRowRoute, ...contractDocumentEntryRowPopupRoute];

@NgModule({
  imports: [RemanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractDocumentEntryRowComponent,
    ContractDocumentEntryRowDetailComponent,
    ContractDocumentEntryRowUpdateComponent,
    ContractDocumentEntryRowDeleteDialogComponent,
    ContractDocumentEntryRowDeletePopupComponent
  ],
  entryComponents: [
    ContractDocumentEntryRowComponent,
    ContractDocumentEntryRowUpdateComponent,
    ContractDocumentEntryRowDeleteDialogComponent,
    ContractDocumentEntryRowDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementContractDocumentEntryRowModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
