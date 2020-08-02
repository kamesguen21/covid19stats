import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19StatsTestModule } from '../../../test.module';
import { GlobalConfigurationsComponent } from 'app/entities/global-configurations/global-configurations.component';
import { GlobalConfigurationsService } from 'app/entities/global-configurations/global-configurations.service';
import { GlobalConfigurations } from 'app/shared/model/global-configurations.model';

describe('Component Tests', () => {
  describe('GlobalConfigurations Management Component', () => {
    let comp: GlobalConfigurationsComponent;
    let fixture: ComponentFixture<GlobalConfigurationsComponent>;
    let service: GlobalConfigurationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [GlobalConfigurationsComponent],
      })
        .overrideTemplate(GlobalConfigurationsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GlobalConfigurationsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GlobalConfigurationsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GlobalConfigurations('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.globalConfigurations && comp.globalConfigurations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
