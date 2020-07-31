import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDailyCases, DailyCases } from 'app/shared/model/daily-cases.model';
import { DailyCasesService } from './daily-cases.service';
import { DailyCasesComponent } from './daily-cases.component';
import { DailyCasesDetailComponent } from './daily-cases-detail.component';
import { DailyCasesUpdateComponent } from './daily-cases-update.component';

@Injectable({ providedIn: 'root' })
export class DailyCasesResolve implements Resolve<IDailyCases> {
  constructor(private service: DailyCasesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDailyCases> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dailyCases: HttpResponse<DailyCases>) => {
          if (dailyCases.body) {
            return of(dailyCases.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DailyCases());
  }
}

export const dailyCasesRoute: Routes = [
  {
    path: '',
    component: DailyCasesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DailyCases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DailyCasesDetailComponent,
    resolve: {
      dailyCases: DailyCasesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DailyCases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DailyCasesUpdateComponent,
    resolve: {
      dailyCases: DailyCasesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DailyCases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DailyCasesUpdateComponent,
    resolve: {
      dailyCases: DailyCasesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DailyCases',
    },
    canActivate: [UserRouteAccessService],
  },
];
