import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19StatsSharedModule } from 'app/shared/shared.module';
import { CdcNewsComponent } from './cdc-news.component';
import { CdcNewsDetailComponent } from './cdc-news-detail.component';
import { CdcNewsUpdateComponent } from './cdc-news-update.component';
import { CdcNewsDeleteDialogComponent } from './cdc-news-delete-dialog.component';
import { cdcNewsRoute } from './cdc-news.route';

@NgModule({
  imports: [Covid19StatsSharedModule, RouterModule.forChild(cdcNewsRoute)],
  declarations: [CdcNewsComponent, CdcNewsDetailComponent, CdcNewsUpdateComponent, CdcNewsDeleteDialogComponent],
  entryComponents: [CdcNewsDeleteDialogComponent],
})
export class Covid19StatsCdcNewsModule {}
