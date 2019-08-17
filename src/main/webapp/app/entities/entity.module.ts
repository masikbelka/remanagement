import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tenant',
        loadChildren: () => import('./tenant/tenant.module').then(m => m.RemanagementTenantModule)
      },
      {
        path: 'tenant-contract',
        loadChildren: () => import('./tenant-contract/tenant-contract.module').then(m => m.RemanagementTenantContractModule)
      },
      {
        path: 'property',
        loadChildren: () => import('./property/property.module').then(m => m.RemanagementPropertyModule)
      },
      {
        path: 'free-period',
        loadChildren: () => import('./free-period/free-period.module').then(m => m.RemanagementFreePeriodModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.RemanagementLocationModule)
      },
      {
        path: 'contract-document',
        loadChildren: () => import('./contract-document/contract-document.module').then(m => m.RemanagementContractDocumentModule)
      },
      {
        path: 'contract-document-entry-row',
        loadChildren: () =>
          import('./contract-document-entry-row/contract-document-entry-row.module').then(m => m.RemanagementContractDocumentEntryRowModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemanagementEntityModule {}
