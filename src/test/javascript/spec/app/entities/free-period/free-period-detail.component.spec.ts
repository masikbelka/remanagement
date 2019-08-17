/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { FreePeriodDetailComponent } from 'app/entities/free-period/free-period-detail.component';
import { FreePeriod } from 'app/shared/model/free-period.model';

describe('Component Tests', () => {
  describe('FreePeriod Management Detail Component', () => {
    let comp: FreePeriodDetailComponent;
    let fixture: ComponentFixture<FreePeriodDetailComponent>;
    const route = ({ data: of({ freePeriod: new FreePeriod(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [FreePeriodDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FreePeriodDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FreePeriodDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.freePeriod).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
