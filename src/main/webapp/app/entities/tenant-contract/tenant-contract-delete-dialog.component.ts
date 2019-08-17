import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from './tenant-contract.service';

@Component({
  selector: 'jhi-tenant-contract-delete-dialog',
  templateUrl: './tenant-contract-delete-dialog.component.html'
})
export class TenantContractDeleteDialogComponent {
  tenantContract: ITenantContract;

  constructor(
    protected tenantContractService: TenantContractService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tenantContractService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tenantContractListModification',
        content: 'Deleted an tenantContract'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tenant-contract-delete-popup',
  template: ''
})
export class TenantContractDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tenantContract }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TenantContractDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tenantContract = tenantContract;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tenant-contract', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tenant-contract', { outlets: { popup: null } }]);
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
