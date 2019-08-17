import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContractDocument, ContractDocument } from 'app/shared/model/contract-document.model';
import { ContractDocumentService } from './contract-document.service';

@Component({
  selector: 'jhi-contract-document-update',
  templateUrl: './contract-document-update.component.html'
})
export class ContractDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    type: [],
    fileName: []
  });

  constructor(
    protected contractDocumentService: ContractDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contractDocument }) => {
      this.updateForm(contractDocument);
    });
  }

  updateForm(contractDocument: IContractDocument) {
    this.editForm.patchValue({
      id: contractDocument.id,
      code: contractDocument.code,
      type: contractDocument.type,
      fileName: contractDocument.fileName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contractDocument = this.createFromForm();
    if (contractDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.contractDocumentService.update(contractDocument));
    } else {
      this.subscribeToSaveResponse(this.contractDocumentService.create(contractDocument));
    }
  }

  private createFromForm(): IContractDocument {
    return {
      ...new ContractDocument(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      type: this.editForm.get(['type']).value,
      fileName: this.editForm.get(['fileName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractDocument>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
