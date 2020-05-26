import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SnippetsSharedModule } from 'app/shared/shared.module';
import { SnippetsCoreModule } from 'app/core/core.module';
import { SnippetsAppRoutingModule } from './app-routing.module';
import { SnippetsHomeModule } from './home/home.module';
import { SnippetsEntityModule } from './entities/entity.module';
import { SnippetsAppSnippetsManagementModule } from './snippets-management/snippets-management.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SnippetsSharedModule,
    SnippetsCoreModule,
    SnippetsHomeModule,
    SnippetsAppSnippetsManagementModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SnippetsEntityModule,
    SnippetsAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SnippetsAppModule {}
