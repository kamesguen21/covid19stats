import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICdcNews, CdcNews } from 'app/shared/model/cdc-news.model';
import { CdcNewsService } from './cdc-news.service';
import { CdcNewsComponent } from './cdc-news.component';
import { CdcNewsDetailComponent } from './cdc-news-detail.component';
import { CdcNewsUpdateComponent } from './cdc-news-update.component';

@Injectable({ providedIn: 'root' })
export class CdcNewsResolve implements Resolve<ICdcNews> {
  constructor(private service: CdcNewsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICdcNews> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cdcNews: HttpResponse<CdcNews>) => {
          if (cdcNews.body) {
            return of(cdcNews.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CdcNews());
  }
}

export const cdcNewsRoute: Routes = [
  {
    path: '',
    component: CdcNewsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CdcNews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CdcNewsDetailComponent,
    resolve: {
      cdcNews: CdcNewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CdcNews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CdcNewsUpdateComponent,
    resolve: {
      cdcNews: CdcNewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CdcNews',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CdcNewsUpdateComponent,
    resolve: {
      cdcNews: CdcNewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CdcNews',
    },
    canActivate: [UserRouteAccessService],
  },
];
