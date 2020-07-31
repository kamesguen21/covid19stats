import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19StatsSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Covid19StatsSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class Covid19StatsHomeModule {}
