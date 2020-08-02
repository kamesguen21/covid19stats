import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGlobalConfigurations } from 'app/shared/model/global-configurations.model';
import { GlobalConfigurationsService } from './global-configurations.service';
import { GlobalConfigurationsDeleteDialogComponent } from './global-configurations-delete-dialog.component';

@Component({
  selector: 'jhi-global-configurations',
  templateUrl: './global-configurations.component.html',
})
export class GlobalConfigurationsComponent implements OnInit, OnDestroy {
  globalConfigurations?: IGlobalConfigurations[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected globalConfigurationsService: GlobalConfigurationsService,
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
      this.globalConfigurationsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IGlobalConfigurations[]>) => (this.globalConfigurations = res.body || []));
      return;
    }

    this.globalConfigurationsService
      .query()
      .subscribe((res: HttpResponse<IGlobalConfigurations[]>) => (this.globalConfigurations = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGlobalConfigurations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGlobalConfigurations): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGlobalConfigurations(): void {
    this.eventSubscriber = this.eventManager.subscribe('globalConfigurationsListModification', () => this.loadAll());
  }

  delete(globalConfigurations: IGlobalConfigurations): void {
    const modalRef = this.modalService.open(GlobalConfigurationsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.globalConfigurations = globalConfigurations;
  }
}
