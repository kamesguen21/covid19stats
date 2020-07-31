import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19StatsTestModule } from '../../../test.module';
import { DailyCasesComponent } from 'app/entities/daily-cases/daily-cases.component';
import { DailyCasesService } from 'app/entities/daily-cases/daily-cases.service';
import { DailyCases } from 'app/shared/model/daily-cases.model';

describe('Component Tests', () => {
  describe('DailyCases Management Component', () => {
    let comp: DailyCasesComponent;
    let fixture: ComponentFixture<DailyCasesComponent>;
    let service: DailyCasesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [DailyCasesComponent],
      })
        .overrideTemplate(DailyCasesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyCasesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyCasesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DailyCases('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dailyCases && comp.dailyCases[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
