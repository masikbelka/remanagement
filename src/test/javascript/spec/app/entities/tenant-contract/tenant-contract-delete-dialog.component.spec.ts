/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RemanagementTestModule } from '../../../test.module';
import { TenantContractDeleteDialogComponent } from 'app/entities/tenant-contract/tenant-contract-delete-dialog.component';
import { TenantContractService } from 'app/entities/tenant-contract/tenant-contract.service';

describe('Component Tests', () => {
  describe('TenantContract Management Delete Component', () => {
    let comp: TenantContractDeleteDialogComponent;
    let fixture: ComponentFixture<TenantContractDeleteDialogComponent>;
    let service: TenantContractService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [TenantContractDeleteDialogComponent]
      })
        .overrideTemplate(TenantContractDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TenantContractDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TenantContractService);
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
