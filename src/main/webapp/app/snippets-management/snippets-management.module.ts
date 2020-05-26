import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SnippetsSharedModule } from '../shared/shared.module';

import { SNIPPETS_MANAGEMENT_ROUTE, SnippetsManagementComponent } from './';
import { PageOneComponent } from './page-one/page-one.component';
import { PageTwoComponent } from './page-two/page-two.component';

@NgModule({
  imports: [SnippetsSharedModule, RouterModule.forRoot([SNIPPETS_MANAGEMENT_ROUTE], { useHash: true })],
  declarations: [SnippetsManagementComponent, PageOneComponent, PageTwoComponent],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SnippetsAppSnippetsManagementModule {}
