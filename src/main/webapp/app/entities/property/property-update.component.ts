import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProperty, Property } from 'app/shared/model/property.model';
import { PropertyService } from './property.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';
import { ITenantContract } from 'app/shared/model/tenant-contract.model';
import { TenantContractService } from 'app/entities/tenant-contract';

@Component({
  selector: 'jhi-property-update',
  templateUrl: './property-update.component.html'
})
export class PropertyUpdateComponent implements OnInit {
  isSaving: boolean;

  locations: ILocation[];

  tenantcontracts: ITenantContract[];

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [],
    number: [],
    type: [],
    bedrooms: [],
    furnishing: [],
    electricity: [],
    water: [],
    parking: [],
    location: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected propertyService: PropertyService,
    protected locationService: LocationService,
    protected tenantContractService: TenantContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ property }) => {
      this.updateForm(property);
    });
    this.locationService
      .query({ filter: 'property-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe(
        (res: ILocation[]) => {
          if (!this.editForm.get('location').value || !this.editForm.get('location').value.id) {
            this.locations = res;
          } else {
            this.locationService
              .find(this.editForm.get('location').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ILocation>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ILocation>) => subResponse.body)
              )
              .subscribe(
                (subRes: ILocation) => (this.locations = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.tenantContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITenantContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITenantContract[]>) => response.body)
      )
      .subscribe((res: ITenantContract[]) => (this.tenantcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(property: IProperty) {
    this.editForm.patchValue({
      id: property.id,
      code: property.code,
      name: property.name,
      number: property.number,
      type: property.type,
      bedrooms: property.bedrooms,
      furnishing: property.furnishing,
      electricity: property.electricity,
      water: property.water,
      parking: property.parking,
      location: property.location
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const property = this.createFromForm();
    if (property.id !== undefined) {
      this.subscribeToSaveResponse(this.propertyService.update(property));
    } else {
      this.subscribeToSaveResponse(this.propertyService.create(property));
    }
  }

  private createFromForm(): IProperty {
    return {
      ...new Property(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      number: this.editForm.get(['number']).value,
      type: this.editForm.get(['type']).value,
      bedrooms: this.editForm.get(['bedrooms']).value,
      furnishing: this.editForm.get(['furnishing']).value,
      electricity: this.editForm.get(['electricity']).value,
      water: this.editForm.get(['water']).value,
      parking: this.editForm.get(['parking']).value,
      location: this.editForm.get(['location']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProperty>>) {
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

  trackLocationById(index: number, item: ILocation) {
    return item.id;
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
