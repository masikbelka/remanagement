import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFreePeriod } from 'app/shared/model/free-period.model';
import { FreePeriodService } from './free-period.service';

@Component({
  selector: 'jhi-free-period-delete-dialog',
  templateUrl: './free-period-delete-dialog.component.html'
})
export class FreePeriodDeleteDialogComponent {
  freePeriod: IFreePeriod;

  constructor(
    protected freePeriodService: FreePeriodService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.freePeriodService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'freePeriodListModification',
        content: 'Deleted an freePeriod'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-free-period-delete-popup',
  template: ''
})
export class FreePeriodDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ freePeriod }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FreePeriodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.freePeriod = freePeriod;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/free-period', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/free-period', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
