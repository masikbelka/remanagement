/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { TenantContractUpdateComponent } from 'app/entities/tenant-contract/tenant-contract-update.component';
import { TenantContractService } from 'app/entities/tenant-contract/tenant-contract.service';
import { TenantContract } from 'app/shared/model/tenant-contract.model';

describe('Component Tests', () => {
  describe('TenantContract Management Update Component', () => {
    let comp: TenantContractUpdateComponent;
    let fixture: ComponentFixture<TenantContractUpdateComponent>;
    let service: TenantContractService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [TenantContractUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TenantContractUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TenantContractUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TenantContractService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TenantContract(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TenantContract();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
