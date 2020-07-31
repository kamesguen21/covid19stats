import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { DailyCasesUpdateComponent } from 'app/entities/daily-cases/daily-cases-update.component';
import { DailyCasesService } from 'app/entities/daily-cases/daily-cases.service';
import { DailyCases } from 'app/shared/model/daily-cases.model';

describe('Component Tests', () => {
  describe('DailyCases Management Update Component', () => {
    let comp: DailyCasesUpdateComponent;
    let fixture: ComponentFixture<DailyCasesUpdateComponent>;
    let service: DailyCasesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [DailyCasesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DailyCasesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyCasesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyCasesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyCases('123');
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
        const entity = new DailyCases();
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
