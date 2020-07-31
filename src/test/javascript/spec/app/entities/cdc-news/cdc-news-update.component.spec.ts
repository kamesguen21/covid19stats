import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Covid19StatsTestModule } from '../../../test.module';
import { CdcNewsUpdateComponent } from 'app/entities/cdc-news/cdc-news-update.component';
import { CdcNewsService } from 'app/entities/cdc-news/cdc-news.service';
import { CdcNews } from 'app/shared/model/cdc-news.model';

describe('Component Tests', () => {
  describe('CdcNews Management Update Component', () => {
    let comp: CdcNewsUpdateComponent;
    let fixture: ComponentFixture<CdcNewsUpdateComponent>;
    let service: CdcNewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19StatsTestModule],
        declarations: [CdcNewsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CdcNewsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CdcNewsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdcNewsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CdcNews('123');
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
        const entity = new CdcNews();
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
