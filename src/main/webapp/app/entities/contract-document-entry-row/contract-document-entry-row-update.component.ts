import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContractDocumentEntryRow, ContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';
import { ContractDocumentEntryRowService } from './contract-document-entry-row.service';

@Component({
  selector: 'jhi-contract-document-entry-row-update',
  templateUrl: './contract-document-entry-row-update.component.html'
})
export class ContractDocumentEntryRowUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    position: [],
    textEn: [],
    textAr: []
  });

  constructor(
    protected contractDocumentEntryRowService: ContractDocumentEntryRowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contractDocumentEntryRow }) => {
      this.updateForm(contractDocumentEntryRow);
    });
  }

  updateForm(contractDocumentEntryRow: IContractDocumentEntryRow) {
    this.editForm.patchValue({
      id: contractDocumentEntryRow.id,
      position: contractDocumentEntryRow.position,
      textEn: contractDocumentEntryRow.textEn,
      textAr: contractDocumentEntryRow.textAr
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contractDocumentEntryRow = this.createFromForm();
    if (contractDocumentEntryRow.id !== undefined) {
      this.subscribeToSaveResponse(this.contractDocumentEntryRowService.update(contractDocumentEntryRow));
    } else {
      this.subscribeToSaveResponse(this.contractDocumentEntryRowService.create(contractDocumentEntryRow));
    }
  }

  private createFromForm(): IContractDocumentEntryRow {
    return {
      ...new ContractDocumentEntryRow(),
      id: this.editForm.get(['id']).value,
      position: this.editForm.get(['position']).value,
      textEn: this.editForm.get(['textEn']).value,
      textAr: this.editForm.get(['textAr']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractDocumentEntryRow>>) {
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
