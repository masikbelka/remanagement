import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';
import { ContractDocumentEntryRowService } from './contract-document-entry-row.service';

@Component({
  selector: 'jhi-contract-document-entry-row-delete-dialog',
  templateUrl: './contract-document-entry-row-delete-dialog.component.html'
})
export class ContractDocumentEntryRowDeleteDialogComponent {
  contractDocumentEntryRow: IContractDocumentEntryRow;

  constructor(
    protected contractDocumentEntryRowService: ContractDocumentEntryRowService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractDocumentEntryRowService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractDocumentEntryRowListModification',
        content: 'Deleted an contractDocumentEntryRow'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-document-entry-row-delete-popup',
  template: ''
})
export class ContractDocumentEntryRowDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractDocumentEntryRow }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractDocumentEntryRowDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.contractDocumentEntryRow = contractDocumentEntryRow;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract-document-entry-row', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract-document-entry-row', { outlets: { popup: null } }]);
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
