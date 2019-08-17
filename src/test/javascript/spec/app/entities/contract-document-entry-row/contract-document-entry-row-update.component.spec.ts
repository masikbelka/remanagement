/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentEntryRowUpdateComponent } from 'app/entities/contract-document-entry-row/contract-document-entry-row-update.component';
import { ContractDocumentEntryRowService } from 'app/entities/contract-document-entry-row/contract-document-entry-row.service';
import { ContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';

describe('Component Tests', () => {
  describe('ContractDocumentEntryRow Management Update Component', () => {
    let comp: ContractDocumentEntryRowUpdateComponent;
    let fixture: ComponentFixture<ContractDocumentEntryRowUpdateComponent>;
    let service: ContractDocumentEntryRowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentEntryRowUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContractDocumentEntryRowUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractDocumentEntryRowUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractDocumentEntryRowService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContractDocumentEntryRow(123);
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
        const entity = new ContractDocumentEntryRow();
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
