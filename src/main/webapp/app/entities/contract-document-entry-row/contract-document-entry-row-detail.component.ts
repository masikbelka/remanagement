import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';

@Component({
  selector: 'jhi-contract-document-entry-row-detail',
  templateUrl: './contract-document-entry-row-detail.component.html'
})
export class ContractDocumentEntryRowDetailComponent implements OnInit {
  contractDocumentEntryRow: IContractDocumentEntryRow;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractDocumentEntryRow }) => {
      this.contractDocumentEntryRow = contractDocumentEntryRow;
    });
  }

  previousState() {
    window.history.back();
  }
}
