import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { GlobalConfigurationsDetailComponent } from 'app/entities/global-configurations/global-configurations-detail.component';
import { GlobalConfigurations } from 'app/shared/model/global-configurations.model';

describe('Component Tests', () => {
  describe('GlobalConfigurations Management Detail Component', () => {
    let comp: GlobalConfigurationsDetailComponent;
    let fixture: ComponentFixture<GlobalConfigurationsDetailComponent>;
    const route = ({ data: of({ globalConfigurations: new GlobalConfigurations('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [GlobalConfigurationsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GlobalConfigurationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GlobalConfigurationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load globalConfigurations on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.globalConfigurations).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
