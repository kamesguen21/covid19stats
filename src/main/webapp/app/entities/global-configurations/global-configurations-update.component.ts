import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGlobalConfigurations, GlobalConfigurations } from 'app/shared/model/global-configurations.model';
import { GlobalConfigurationsService } from './global-configurations.service';

@Component({
  selector: 'jhi-global-configurations-update',
  templateUrl: './global-configurations-update.component.html',
})
export class GlobalConfigurationsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    discription: [],
    host: [],
    code: [],
  });

  constructor(
    protected globalConfigurationsService: GlobalConfigurationsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ globalConfigurations }) => {
      this.updateForm(globalConfigurations);
    });
  }

  updateForm(globalConfigurations: IGlobalConfigurations): void {
    this.editForm.patchValue({
      id: globalConfigurations.id,
      name: globalConfigurations.name,
      discription: globalConfigurations.discription,
      host: globalConfigurations.host,
      code: globalConfigurations.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const globalConfigurations = this.createFromForm();
    if (globalConfigurations.id !== undefined) {
      this.subscribeToSaveResponse(this.globalConfigurationsService.update(globalConfigurations));
    } else {
      this.subscribeToSaveResponse(this.globalConfigurationsService.create(globalConfigurations));
    }
  }

  private createFromForm(): IGlobalConfigurations {
    return {
      ...new GlobalConfigurations(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      discription: this.editForm.get(['discription'])!.value,
      host: this.editForm.get(['host'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGlobalConfigurations>>): void {
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
