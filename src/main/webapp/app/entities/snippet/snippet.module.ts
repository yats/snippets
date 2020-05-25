import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SnippetsSharedModule } from 'app/shared/shared.module';
import { SnippetComponent } from './snippet.component';
import { SnippetDetailComponent } from './snippet-detail.component';
import { SnippetUpdateComponent } from './snippet-update.component';
import { SnippetDeleteDialogComponent } from './snippet-delete-dialog.component';
import { snippetRoute } from './snippet.route';

@NgModule({
  imports: [SnippetsSharedModule, RouterModule.forChild(snippetRoute)],
  declarations: [SnippetComponent, SnippetDetailComponent, SnippetUpdateComponent, SnippetDeleteDialogComponent],
  entryComponents: [SnippetDeleteDialogComponent],
})
export class SnippetsSnippetModule {}
