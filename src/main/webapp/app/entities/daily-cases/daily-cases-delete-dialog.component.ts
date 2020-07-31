import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDailyCases } from 'app/shared/model/daily-cases.model';
import { DailyCasesService } from './daily-cases.service';

@Component({
  templateUrl: './daily-cases-delete-dialog.component.html',
})
export class DailyCasesDeleteDialogComponent {
  dailyCases?: IDailyCases;

  constructor(
    protected dailyCasesService: DailyCasesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dailyCasesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dailyCasesListModification');
      this.activeModal.close();
    });
  }
}
