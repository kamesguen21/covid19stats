import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.Covid19StatsCountryModule),
      },
      {
        path: 'daily-cases',
        loadChildren: () => import('./daily-cases/daily-cases.module').then(m => m.Covid19StatsDailyCasesModule),
      },
      {
        path: 'cdc-news',
        loadChildren: () => import('./cdc-news/cdc-news.module').then(m => m.Covid19StatsCdcNewsModule),
      },
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.Covid19StatsItemModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.Covid19StatsCategoryModule),
      },
      {
        path: 'global-configurations',
        loadChildren: () =>
          import('./global-configurations/global-configurations.module').then(m => m.Covid19StatsGlobalConfigurationsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Covid19StatsEntityModule {}
