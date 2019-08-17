import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractDocument } from 'app/shared/model/contract-document.model';

@Component({
  selector: 'jhi-contract-document-detail',
  templateUrl: './contract-document-detail.component.html'
})
export class ContractDocumentDetailComponent implements OnInit {
  contractDocument: IContractDocument;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractDocument }) => {
      this.contractDocument = contractDocument;
    });
  }

  previousState() {
    window.history.back();
  }
}
