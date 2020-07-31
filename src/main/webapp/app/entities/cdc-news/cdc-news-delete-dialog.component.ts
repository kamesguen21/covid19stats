import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICdcNews } from 'app/shared/model/cdc-news.model';
import { CdcNewsService } from './cdc-news.service';

@Component({
  templateUrl: './cdc-news-delete-dialog.component.html',
})
export class CdcNewsDeleteDialogComponent {
  cdcNews?: ICdcNews;

  constructor(protected cdcNewsService: CdcNewsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.cdcNewsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cdcNewsListModification');
      this.activeModal.close();
    });
  }
}
