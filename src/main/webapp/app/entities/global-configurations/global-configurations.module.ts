import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19StatsSharedModule } from 'app/shared/shared.module';
import { GlobalConfigurationsComponent } from './global-configurations.component';
import { GlobalConfigurationsDetailComponent } from './global-configurations-detail.component';
import { GlobalConfigurationsUpdateComponent } from './global-configurations-update.component';
import { GlobalConfigurationsDeleteDialogComponent } from './global-configurations-delete-dialog.component';
import { globalConfigurationsRoute } from './global-configurations.route';

@NgModule({
  imports: [Covid19StatsSharedModule, RouterModule.forChild(globalConfigurationsRoute)],
  declarations: [
    GlobalConfigurationsComponent,
    GlobalConfigurationsDetailComponent,
    GlobalConfigurationsUpdateComponent,
    GlobalConfigurationsDeleteDialogComponent,
  ],
  entryComponents: [GlobalConfigurationsDeleteDialogComponent],
})
export class Covid19StatsGlobalConfigurationsModule {}
