import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGlobalConfigurations } from 'app/shared/model/global-configurations.model';

@Component({
  selector: 'jhi-global-configurations-detail',
  templateUrl: './global-configurations-detail.component.html',
})
export class GlobalConfigurationsDetailComponent implements OnInit {
  globalConfigurations: IGlobalConfigurations | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ globalConfigurations }) => (this.globalConfigurations = globalConfigurations));
  }

  previousState(): void {
    window.history.back();
  }
}
