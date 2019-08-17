import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITenantContract, TenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from './tenant-contract.service';
import { IFreePeriod } from 'app/shared/model/free-period.model';
import { FreePeriodService } from 'app/entities/free-period';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { ITenant } from 'app/shared/model/tenant.model';
import { TenantService } from 'app/entities/tenant';
import { IContractDocument } from 'app/shared/model/contract-document.model';
import { ContractDocumentService } from 'app/entities/contract-document';

@Component({
  selector: 'jhi-tenant-contract-update',
  templateUrl: './tenant-contract-update.component.html'
})
export class TenantContractUpdateComponent implements OnInit {
  isSaving: boolean;

  freeperiods: IFreePeriod[];

  properties: IProperty[];

  tenants: ITenant[];

  contractdocuments: IContractDocument[];

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    effectiveDate: [],
    startDate: [],
    endDate: [],
    rent: [],
    deposit: [],
    utilities: [],
    freePeriods: [],
    properties: [],
    tenants: [],
    contractDocument: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tenantContractService: TenantContractService,
    protected freePeriodService: FreePeriodService,
    protected propertyService: PropertyService,
    protected tenantService: TenantService,
    protected contractDocumentService: ContractDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tenantContract }) => {
      this.updateForm(tenantContract);
    });
    this.freePeriodService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFreePeriod[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFreePeriod[]>) => response.body)
      )
      .subscribe((res: IFreePeriod[]) => (this.freeperiods = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.propertyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProperty[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProperty[]>) => response.body)
      )
      .subscribe((res: IProperty[]) => (this.properties = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tenantService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITenant[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITenant[]>) => response.body)
      )
      .subscribe((res: ITenant[]) => (this.tenants = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.contractDocumentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContractDocument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContractDocument[]>) => response.body)
      )
      .subscribe((res: IContractDocument[]) => (this.contractdocuments = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tenantContract: ITenantContract) {
    this.editForm.patchValue({
      id: tenantContract.id,
      code: tenantContract.code,
      effectiveDate: tenantContract.effectiveDate != null ? tenantContract.effectiveDate.format(DATE_TIME_FORMAT) : null,
      startDate: tenantContract.startDate != null ? tenantContract.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: tenantContract.endDate != null ? tenantContract.endDate.format(DATE_TIME_FORMAT) : null,
      rent: tenantContract.rent,
      deposit: tenantContract.deposit,
      utilities: tenantContract.utilities,
      freePeriods: tenantContract.freePeriods,
      properties: tenantContract.properties,
      tenants: tenantContract.tenants,
      contractDocument: tenantContract.contractDocument
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tenantContract = this.createFromForm();
    if (tenantContract.id !== undefined) {
      this.subscribeToSaveResponse(this.tenantContractService.update(tenantContract));
    } else {
      this.subscribeToSaveResponse(this.tenantContractService.create(tenantContract));
    }
  }

  private createFromForm(): ITenantContract {
    return {
      ...new TenantContract(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      effectiveDate:
        this.editForm.get(['effectiveDate']).value != null
          ? moment(this.editForm.get(['effectiveDate']).value, DATE_TIME_FORMAT)
          : undefined,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      rent: this.editForm.get(['rent']).value,
      deposit: this.editForm.get(['deposit']).value,
      utilities: this.editForm.get(['utilities']).value,
      freePeriods: this.editForm.get(['freePeriods']).value,
      properties: this.editForm.get(['properties']).value,
      tenants: this.editForm.get(['tenants']).value,
      contractDocument: this.editForm.get(['contractDocument']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITenantContract>>) {
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

  trackFreePeriodById(index: number, item: IFreePeriod) {
    return item.id;
  }

  trackPropertyById(index: number, item: IProperty) {
    return item.id;
  }

  trackTenantById(index: number, item: ITenant) {
    return item.id;
  }

  trackContractDocumentById(index: number, item: IContractDocument) {
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
