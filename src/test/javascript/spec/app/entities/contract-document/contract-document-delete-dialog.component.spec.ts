/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentDeleteDialogComponent } from 'app/entities/contract-document/contract-document-delete-dialog.component';
import { ContractDocumentService } from 'app/entities/contract-document/contract-document.service';

describe('Component Tests', () => {
  describe('ContractDocument Management Delete Component', () => {
    let comp: ContractDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<ContractDocumentDeleteDialogComponent>;
    let service: ContractDocumentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentDeleteDialogComponent]
      })
        .overrideTemplate(ContractDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractDocumentService);
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
