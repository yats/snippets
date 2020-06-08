import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SnippetsSharedModule } from 'app/shared/shared.module';
import { SnippetComponent } from './snippet.component';
import { PAGE_SNIPPET } from './snippet.route';

@NgModule({
  imports: [SnippetsSharedModule, RouterModule.forChild(PAGE_SNIPPET)],
  declarations: [SnippetComponent],
})
export class SnippetsSnippetModule {}
