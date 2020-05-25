import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'snippet',
        loadChildren: () => import('./snippet/snippet.module').then(m => m.SnippetsSnippetModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.SnippetsCategoryModule),
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.SnippetsTagModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SnippetsEntityModule {}
