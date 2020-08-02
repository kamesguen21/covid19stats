import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { GlobalConfigurationsUpdateComponent } from 'app/entities/global-configurations/global-configurations-update.component';
import { GlobalConfigurationsService } from 'app/entities/global-configurations/global-configurations.service';
import { GlobalConfigurations } from 'app/shared/model/global-configurations.model';

describe('Component Tests', () => {
  describe('GlobalConfigurations Management Update Component', () => {
    let comp: GlobalConfigurationsUpdateComponent;
    let fixture: ComponentFixture<GlobalConfigurationsUpdateComponent>;
    let service: GlobalConfigurationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [GlobalConfigurationsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GlobalConfigurationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GlobalConfigurationsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GlobalConfigurationsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GlobalConfigurations('123');
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
        const entity = new GlobalConfigurations();
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
