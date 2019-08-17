import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RemanagementSharedModule } from 'app/shared';
import {
  FreePeriodComponent,
  FreePeriodDetailComponent,
  FreePeriodUpdateComponent,
  FreePeriodDeletePopupComponent,
  FreePeriodDeleteDialogComponent,
  freePeriodRoute,
  freePeriodPopupRoute
} from './';

const ENTITY_STATES = [...freePeriodRoute, ...freePeriodPopupRoute];

@NgModule({
  imports: [RemanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FreePeriodComponent,
    FreePeriodDetailComponent,
    FreePeriodUpdateComponent,
    FreePeriodDeleteDialogComponent,
    FreePeriodDeletePopupComponent
  ],
  entryComponents: [FreePeriodComponent, FreePeriodUpdateComponent, FreePeriodDeleteDialogComponent, FreePeriodDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementFreePeriodModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
