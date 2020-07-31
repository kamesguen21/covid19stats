import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { CdcNewsDetailComponent } from 'app/entities/cdc-news/cdc-news-detail.component';
import { CdcNews } from 'app/shared/model/cdc-news.model';

describe('Component Tests', () => {
  describe('CdcNews Management Detail Component', () => {
    let comp: CdcNewsDetailComponent;
    let fixture: ComponentFixture<CdcNewsDetailComponent>;
    const route = ({ data: of({ cdcNews: new CdcNews('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [CdcNewsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CdcNewsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CdcNewsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cdcNews on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cdcNews).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
