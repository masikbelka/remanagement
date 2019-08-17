import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IFreePeriod, FreePeriod } from 'app/shared/model/free-period.model';
import { FreePeriodService } from './free-period.service';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from 'app/entities/tenant-contract';

@Component({
  selector: 'jhi-free-period-update',
  templateUrl: './free-period-update.component.html'
})
export class FreePeriodUpdateComponent implements OnInit {
  isSaving: boolean;

  tenantcontracts: ITenantContract[];

  editForm = this.fb.group({
    id: [],
    code: [],
    startDate: [],
    endDate: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected freePeriodService: FreePeriodService,
    protected tenantContractService: TenantContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ freePeriod }) => {
      this.updateForm(freePeriod);
    });
    this.tenantContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITenantContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITenantContract[]>) => response.body)
      )
      .subscribe((res: ITenantContract[]) => (this.tenantcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(freePeriod: IFreePeriod) {
    this.editForm.patchValue({
      id: freePeriod.id,
      code: freePeriod.code,
      startDate: freePeriod.startDate != null ? freePeriod.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: freePeriod.endDate != null ? freePeriod.endDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const freePeriod = this.createFromForm();
    if (freePeriod.id !== undefined) {
      this.subscribeToSaveResponse(this.freePeriodService.update(freePeriod));
    } else {
      this.subscribeToSaveResponse(this.freePeriodService.create(freePeriod));
    }
  }

  private createFromForm(): IFreePeriod {
    return {
      ...new FreePeriod(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFreePeriod>>) {
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
