import { TagComponent } from './tag/tag.component';
import { CategoryComponent } from './category/category.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SnippetsSharedModule } from '../shared/shared.module';

import { SNIPPETS_MANAGEMENT_ROUTE, SnippetsManagementComponent } from './';
import { PageOneComponent } from './page-one/page-one.component';
import { PageTwoComponent } from './page-two/page-two.component';
import { SnippetComponent } from './snippet/snippet.component';

@NgModule({
  imports: [SnippetsSharedModule, RouterModule.forRoot([SNIPPETS_MANAGEMENT_ROUTE], { useHash: true })],
  declarations: [SnippetsManagementComponent, PageOneComponent, PageTwoComponent, SnippetComponent, CategoryComponent, TagComponent],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SnippetsAppSnippetsManagementModule {}
