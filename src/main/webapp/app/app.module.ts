import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Covid19StatsSharedModule } from 'app/shared/shared.module';
import { Covid19StatsCoreModule } from 'app/core/core.module';
import { Covid19StatsAppRoutingModule } from './app-routing.module';
import { Covid19StatsHomeModule } from './home/home.module';
import { Covid19StatsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Covid19StatsSharedModule,
    Covid19StatsCoreModule,
    Covid19StatsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Covid19StatsEntityModule,
    Covid19StatsAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class Covid19StatsAppModule {}
