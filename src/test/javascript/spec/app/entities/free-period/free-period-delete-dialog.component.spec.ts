/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RemanagementTestModule } from '../../../test.module';
import { FreePeriodDeleteDialogComponent } from 'app/entities/free-period/free-period-delete-dialog.component';
import { FreePeriodService } from 'app/entities/free-period/free-period.service';

describe('Component Tests', () => {
  describe('FreePeriod Management Delete Component', () => {
    let comp: FreePeriodDeleteDialogComponent;
    let fixture: ComponentFixture<FreePeriodDeleteDialogComponent>;
    let service: FreePeriodService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [FreePeriodDeleteDialogComponent]
      })
        .overrideTemplate(FreePeriodDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FreePeriodDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FreePeriodService);
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
