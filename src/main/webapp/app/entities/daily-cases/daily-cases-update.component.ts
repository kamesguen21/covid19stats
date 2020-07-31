import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDailyCases, DailyCases } from 'app/shared/model/daily-cases.model';
import { DailyCasesService } from './daily-cases.service';

@Component({
  selector: 'jhi-daily-cases-update',
  templateUrl: './daily-cases-update.component.html',
})
export class DailyCasesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    country: [],
    countryCode: [],
    province: [],
    city: [],
    cityCode: [],
    lat: [],
    lon: [],
    cases: [],
    status: [],
    date: [],
  });

  constructor(protected dailyCasesService: DailyCasesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyCases }) => {
      if (!dailyCases.id) {
        const today = moment().startOf('day');
        dailyCases.date = today;
      }

      this.updateForm(dailyCases);
    });
  }

  updateForm(dailyCases: IDailyCases): void {
    this.editForm.patchValue({
      id: dailyCases.id,
      country: dailyCases.country,
      countryCode: dailyCases.countryCode,
      province: dailyCases.province,
      city: dailyCases.city,
      cityCode: dailyCases.cityCode,
      lat: dailyCases.lat,
      lon: dailyCases.lon,
      cases: dailyCases.cases,
      status: dailyCases.status,
      date: dailyCases.date ? dailyCases.date.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dailyCases = this.createFromForm();
    if (dailyCases.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyCasesService.update(dailyCases));
    } else {
      this.subscribeToSaveResponse(this.dailyCasesService.create(dailyCases));
    }
  }

  private createFromForm(): IDailyCases {
    return {
      ...new DailyCases(),
      id: this.editForm.get(['id'])!.value,
      country: this.editForm.get(['country'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      province: this.editForm.get(['province'])!.value,
      city: this.editForm.get(['city'])!.value,
      cityCode: this.editForm.get(['cityCode'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      cases: this.editForm.get(['cases'])!.value,
      status: this.editForm.get(['status'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyCases>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
