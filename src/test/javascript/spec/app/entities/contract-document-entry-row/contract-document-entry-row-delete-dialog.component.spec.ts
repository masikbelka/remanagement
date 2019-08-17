/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentEntryRowDeleteDialogComponent } from 'app/entities/contract-document-entry-row/contract-document-entry-row-delete-dialog.component';
import { ContractDocumentEntryRowService } from 'app/entities/contract-document-entry-row/contract-document-entry-row.service';

describe('Component Tests', () => {
  describe('ContractDocumentEntryRow Management Delete Component', () => {
    let comp: ContractDocumentEntryRowDeleteDialogComponent;
    let fixture: ComponentFixture<ContractDocumentEntryRowDeleteDialogComponent>;
    let service: ContractDocumentEntryRowService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentEntryRowDeleteDialogComponent]
      })
        .overrideTemplate(ContractDocumentEntryRowDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractDocumentEntryRowDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractDocumentEntryRowService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
