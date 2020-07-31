import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { DailyCasesDetailComponent } from 'app/entities/daily-cases/daily-cases-detail.component';
import { DailyCases } from 'app/shared/model/daily-cases.model';

describe('Component Tests', () => {
  describe('DailyCases Management Detail Component', () => {
    let comp: DailyCasesDetailComponent;
    let fixture: ComponentFixture<DailyCasesDetailComponent>;
    const route = ({ data: of({ dailyCases: new DailyCases('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [DailyCasesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DailyCasesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyCasesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dailyCases on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dailyCases).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
