import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SnippetsManagementComponent } from './snippets-management.component';
import { PAGE_ONE_ROUTE } from './page-one/page-one.route';
import { PAGE_TWO_ROUTE } from './page-two/page-two.route';

export const SNIPPETS_MANAGEMENT_ROUTE: Route = {
  path: 'snippets-management',
  component: SnippetsManagementComponent,
  data: {
    authorities: [],
    pageTitle: 'snippets-management.title',
  },
  canActivate: [UserRouteAccessService],
  children: [PAGE_ONE_ROUTE, PAGE_TWO_ROUTE],
};
