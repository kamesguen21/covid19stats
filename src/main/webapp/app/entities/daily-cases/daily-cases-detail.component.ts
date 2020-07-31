import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDailyCases } from 'app/shared/model/daily-cases.model';

@Component({
  selector: 'jhi-daily-cases-detail',
  templateUrl: './daily-cases-detail.component.html',
})
export class DailyCasesDetailComponent implements OnInit {
  dailyCases: IDailyCases | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyCases }) => (this.dailyCases = dailyCases));
  }

  previousState(): void {
    window.history.back();
  }
}
