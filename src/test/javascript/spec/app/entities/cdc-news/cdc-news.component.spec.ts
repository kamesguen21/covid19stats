import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19StatsTestModule } from '../../../test.module';
import { CdcNewsComponent } from 'app/entities/cdc-news/cdc-news.component';
import { CdcNewsService } from 'app/entities/cdc-news/cdc-news.service';
import { CdcNews } from 'app/shared/model/cdc-news.model';

describe('Component Tests', () => {
  describe('CdcNews Management Component', () => {
    let comp: CdcNewsComponent;
    let fixture: ComponentFixture<CdcNewsComponent>;
    let service: CdcNewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [CdcNewsComponent],
      })
        .overrideTemplate(CdcNewsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CdcNewsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdcNewsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CdcNews('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cdcNews && comp.cdcNews[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
