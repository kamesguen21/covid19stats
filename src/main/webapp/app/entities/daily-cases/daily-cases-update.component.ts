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
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

@Component({
  selector: 'jhi-daily-cases-update',
  templateUrl: './daily-cases-update.component.html',
})
export class DailyCasesUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    lat: [],
    lon: [],
    confirmed: [],
    active: [],
    deaths: [],
    recovered: [],
    date: [],
    country: [],
  });

  constructor(
    protected dailyCasesService: DailyCasesService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyCases }) => {
      if (!dailyCases.id) {
        const today = moment().startOf('day');
        dailyCases.date = today;
      }

      this.updateForm(dailyCases);

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
    });
  }

  updateForm(dailyCases: IDailyCases): void {
    this.editForm.patchValue({
      id: dailyCases.id,
      lat: dailyCases.lat,
      lon: dailyCases.lon,
      confirmed: dailyCases.confirmed,
      active: dailyCases.active,
      deaths: dailyCases.deaths,
      recovered: dailyCases.recovered,
      date: dailyCases.date ? dailyCases.date.format(DATE_TIME_FORMAT) : null,
      country: dailyCases.country,
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
      lat: this.editForm.get(['lat'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      confirmed: this.editForm.get(['confirmed'])!.value,
      active: this.editForm.get(['active'])!.value,
      deaths: this.editForm.get(['deaths'])!.value,
      recovered: this.editForm.get(['recovered'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      country: this.editForm.get(['country'])!.value,
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

  trackById(index: number, item: ICountry): any {
    return item.id;
  }
}
