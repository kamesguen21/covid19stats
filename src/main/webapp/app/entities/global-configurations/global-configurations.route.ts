import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGlobalConfigurations, GlobalConfigurations } from 'app/shared/model/global-configurations.model';
import { GlobalConfigurationsService } from './global-configurations.service';
import { GlobalConfigurationsComponent } from './global-configurations.component';
import { GlobalConfigurationsDetailComponent } from './global-configurations-detail.component';
import { GlobalConfigurationsUpdateComponent } from './global-configurations-update.component';

@Injectable({ providedIn: 'root' })
export class GlobalConfigurationsResolve implements Resolve<IGlobalConfigurations> {
  constructor(private service: GlobalConfigurationsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGlobalConfigurations> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((globalConfigurations: HttpResponse<GlobalConfigurations>) => {
          if (globalConfigurations.body) {
            return of(globalConfigurations.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GlobalConfigurations());
  }
}

export const globalConfigurationsRoute: Routes = [
  {
    path: '',
    component: GlobalConfigurationsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GlobalConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GlobalConfigurationsDetailComponent,
    resolve: {
      globalConfigurations: GlobalConfigurationsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GlobalConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GlobalConfigurationsUpdateComponent,
    resolve: {
      globalConfigurations: GlobalConfigurationsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GlobalConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GlobalConfigurationsUpdateComponent,
    resolve: {
      globalConfigurations: GlobalConfigurationsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GlobalConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
];
