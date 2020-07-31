import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICdcNews } from 'app/shared/model/cdc-news.model';

@Component({
  selector: 'jhi-cdc-news-detail',
  templateUrl: './cdc-news-detail.component.html',
})
export class CdcNewsDetailComponent implements OnInit {
  cdcNews: ICdcNews | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cdcNews }) => (this.cdcNews = cdcNews));
  }

  previousState(): void {
    window.history.back();
  }
}
