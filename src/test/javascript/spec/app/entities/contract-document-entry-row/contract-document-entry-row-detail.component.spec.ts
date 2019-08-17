/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentEntryRowDetailComponent } from 'app/entities/contract-document-entry-row/contract-document-entry-row-detail.component';
import { ContractDocumentEntryRow } from 'app/shared/model/contract-document-entry-row.model';

describe('Component Tests', () => {
  describe('ContractDocumentEntryRow Management Detail Component', () => {
    let comp: ContractDocumentEntryRowDetailComponent;
    let fixture: ComponentFixture<ContractDocumentEntryRowDetailComponent>;
    const route = ({ data: of({ contractDocumentEntryRow: new ContractDocumentEntryRow(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentEntryRowDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContractDocumentEntryRowDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractDocumentEntryRowDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contractDocumentEntryRow).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
