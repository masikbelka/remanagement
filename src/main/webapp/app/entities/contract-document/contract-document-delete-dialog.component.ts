import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractDocument } from 'app/shared/model/contract-document.model';
import { ContractDocumentService } from './contract-document.service';

@Component({
  selector: 'jhi-contract-document-delete-dialog',
  templateUrl: './contract-document-delete-dialog.component.html'
})
export class ContractDocumentDeleteDialogComponent {
  contractDocument: IContractDocument;

  constructor(
    protected contractDocumentService: ContractDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractDocumentListModification',
        content: 'Deleted an contractDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-document-delete-popup',
  template: ''
})
export class ContractDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractDocumentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contractDocument = contractDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract-document', { outlets: { popup: null } }]);
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
