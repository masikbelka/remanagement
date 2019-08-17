import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RemanagementSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [RemanagementSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [RemanagementSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementSharedModule {
  static forRoot() {
    return {
      ngModule: RemanagementSharedModule
    };
  }
}
