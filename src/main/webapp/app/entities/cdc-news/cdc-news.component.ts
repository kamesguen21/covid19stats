import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICdcNews } from 'app/shared/model/cdc-news.model';
import { CdcNewsService } from './cdc-news.service';
import { CdcNewsDeleteDialogComponent } from './cdc-news-delete-dialog.component';

@Component({
  selector: 'jhi-cdc-news',
  templateUrl: './cdc-news.component.html',
})
export class CdcNewsComponent implements OnInit, OnDestroy {
  cdcNews?: ICdcNews[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected cdcNewsService: CdcNewsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.cdcNewsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICdcNews[]>) => (this.cdcNews = res.body || []));
      return;
    }

    this.cdcNewsService.query().subscribe((res: HttpResponse<ICdcNews[]>) => (this.cdcNews = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCdcNews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICdcNews): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCdcNews(): void {
    this.eventSubscriber = this.eventManager.subscribe('cdcNewsListModification', () => this.loadAll());
  }

  delete(cdcNews: ICdcNews): void {
    const modalRef = this.modalService.open(CdcNewsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cdcNews = cdcNews;
  }
}
