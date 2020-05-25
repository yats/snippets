import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SnippetsSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [SnippetsSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class SnippetsHomeModule {}
