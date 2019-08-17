/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentUpdateComponent } from 'app/entities/contract-document/contract-document-update.component';
import { ContractDocumentService } from 'app/entities/contract-document/contract-document.service';
import { ContractDocument } from 'app/shared/model/contract-document.model';

describe('Component Tests', () => {
  describe('ContractDocument Management Update Component', () => {
    let comp: ContractDocumentUpdateComponent;
    let fixture: ComponentFixture<ContractDocumentUpdateComponent>;
    let service: ContractDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContractDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContractDocument(123);
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
        const entity = new ContractDocument();
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
