import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGlobalConfigurations } from 'app/shared/model/global-configurations.model';
import { GlobalConfigurationsService } from './global-configurations.service';

@Component({
  templateUrl: './global-configurations-delete-dialog.component.html',
})
export class GlobalConfigurationsDeleteDialogComponent {
  globalConfigurations?: IGlobalConfigurations;

  constructor(
    protected globalConfigurationsService: GlobalConfigurationsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.globalConfigurationsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('globalConfigurationsListModification');
      this.activeModal.close();
    });
  }
}
