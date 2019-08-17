/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { TenantContractDetailComponent } from 'app/entities/tenant-contract/tenant-contract-detail.component';
import { TenantContract } from 'app/shared/model/tenant-contract.model';

describe('Component Tests', () => {
  describe('TenantContract Management Detail Component', () => {
    let comp: TenantContractDetailComponent;
    let fixture: ComponentFixture<TenantContractDetailComponent>;
    const route = ({ data: of({ tenantContract: new TenantContract(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [TenantContractDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TenantContractDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TenantContractDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tenantContract).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
