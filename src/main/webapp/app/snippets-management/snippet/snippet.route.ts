import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISnippet, Snippet } from 'app/shared/model/snippet.model';
import { SnippetService } from './snippet.service';
import { SnippetComponent } from './snippet.component';

@Injectable({ providedIn: 'root' })
export class SnippetResolve implements Resolve<ISnippet> {
  constructor(private service: SnippetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISnippet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((snippet: HttpResponse<Snippet>) => {
          if (snippet.body) {
            return of(snippet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Snippet());
  }
}

export const PAGE_SNIPPET: Routes = [
  {
    path: 'snippet',
    component: SnippetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Snippets',
    },
    canActivate: [UserRouteAccessService],
  },
  // {
  //   path: 'snippet/:id/view',
  //   component: SnippetDetailComponent,
  //   resolve: {
  //     snippet: SnippetResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'Snippets',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: 'snippet/new',
  //   component: SnippetUpdateComponent,
  //   resolve: {
  //     snippet: SnippetResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'Snippets',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: 'snippet/:id/edit',
  //   component: SnippetUpdateComponent,
  //   resolve: {
  //     snippet: SnippetResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'Snippets',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];
