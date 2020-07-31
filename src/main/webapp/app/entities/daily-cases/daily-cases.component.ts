import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyCases } from 'app/shared/model/daily-cases.model';
import { DailyCasesService } from './daily-cases.service';
import { DailyCasesDeleteDialogComponent } from './daily-cases-delete-dialog.component';

@Component({
  selector: 'jhi-daily-cases',
  templateUrl: './daily-cases.component.html',
})
export class DailyCasesComponent implements OnInit, OnDestroy {
  dailyCases?: IDailyCases[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected dailyCasesService: DailyCasesService,
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
      this.dailyCasesService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IDailyCases[]>) => (this.dailyCases = res.body || []));
      return;
    }

    this.dailyCasesService.query().subscribe((res: HttpResponse<IDailyCases[]>) => (this.dailyCases = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDailyCases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDailyCases): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDailyCases(): void {
    this.eventSubscriber = this.eventManager.subscribe('dailyCasesListModification', () => this.loadAll());
  }

  delete(dailyCases: IDailyCases): void {
    const modalRef = this.modalService.open(DailyCasesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyCases = dailyCases;
  }
}
