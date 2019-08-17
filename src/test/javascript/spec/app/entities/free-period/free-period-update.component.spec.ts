/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { FreePeriodUpdateComponent } from 'app/entities/free-period/free-period-update.component';
import { FreePeriodService } from 'app/entities/free-period/free-period.service';
import { FreePeriod } from 'app/shared/model/free-period.model';

describe('Component Tests', () => {
  describe('FreePeriod Management Update Component', () => {
    let comp: FreePeriodUpdateComponent;
    let fixture: ComponentFixture<FreePeriodUpdateComponent>;
    let service: FreePeriodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [FreePeriodUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FreePeriodUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FreePeriodUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FreePeriodService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FreePeriod(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FreePeriod();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
