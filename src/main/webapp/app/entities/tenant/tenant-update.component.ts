import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITenant, Tenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from 'app/entities/tenant-contract';

@Component({
  selector: 'jhi-tenant-update',
  templateUrl: './tenant-update.component.html'
})
export class TenantUpdateComponent implements OnInit {
  isSaving: boolean;

  tenantcontracts: ITenantContract[];

  editForm = this.fb.group({
    id: [],
    qid: [],
    name: [],
    arabicName: [],
    telephone: [],
    fax: [],
    mobile: [],
    email: [],
    pobox: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tenantService: TenantService,
    protected tenantContractService: TenantContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tenant }) => {
      this.updateForm(tenant);
    });
    this.tenantContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITenantContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITenantContract[]>) => response.body)
      )
      .subscribe((res: ITenantContract[]) => (this.tenantcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tenant: ITenant) {
    this.editForm.patchValue({
      id: tenant.id,
      qid: tenant.qid,
      name: tenant.name,
      arabicName: tenant.arabicName,
      telephone: tenant.telephone,
      fax: tenant.fax,
      mobile: tenant.mobile,
      email: tenant.email,
      pobox: tenant.pobox
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tenant = this.createFromForm();
    if (tenant.id !== undefined) {
      this.subscribeToSaveResponse(this.tenantService.update(tenant));
    } else {
      this.subscribeToSaveResponse(this.tenantService.create(tenant));
    }
  }

  private createFromForm(): ITenant {
    return {
      ...new Tenant(),
      id: this.editForm.get(['id']).value,
      qid: this.editForm.get(['qid']).value,
      name: this.editForm.get(['name']).value,
      arabicName: this.editForm.get(['arabicName']).value,
      telephone: this.editForm.get(['telephone']).value,
      fax: this.editForm.get(['fax']).value,
      mobile: this.editForm.get(['mobile']).value,
      email: this.editForm.get(['email']).value,
      pobox: this.editForm.get(['pobox']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITenant>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTenantContractById(index: number, item: ITenantContract) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
