import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19StatsSharedModule } from 'app/shared/shared.module';
import { DailyCasesComponent } from './daily-cases.component';
import { DailyCasesDetailComponent } from './daily-cases-detail.component';
import { DailyCasesUpdateComponent } from './daily-cases-update.component';
import { DailyCasesDeleteDialogComponent } from './daily-cases-delete-dialog.component';
import { dailyCasesRoute } from './daily-cases.route';

@NgModule({
  imports: [Covid19StatsSharedModule, RouterModule.forChild(dailyCasesRoute)],
  declarations: [DailyCasesComponent, DailyCasesDetailComponent, DailyCasesUpdateComponent, DailyCasesDeleteDialogComponent],
  entryComponents: [DailyCasesDeleteDialogComponent],
})
export class Covid19StatsDailyCasesModule {}
