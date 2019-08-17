/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemanagementTestModule } from '../../../test.module';
import { ContractDocumentDetailComponent } from 'app/entities/contract-document/contract-document-detail.component';
import { ContractDocument } from 'app/shared/model/contract-document.model';

describe('Component Tests', () => {
  describe('ContractDocument Management Detail Component', () => {
    let comp: ContractDocumentDetailComponent;
    let fixture: ComponentFixture<ContractDocumentDetailComponent>;
    const route = ({ data: of({ contractDocument: new ContractDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RemanagementTestModule],
        declarations: [ContractDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContractDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contractDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
