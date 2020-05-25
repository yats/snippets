import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISnippet } from 'app/shared/model/snippet.model';

@Component({
  selector: 'jhi-snippet-detail',
  templateUrl: './snippet-detail.component.html',
})
export class SnippetDetailComponent implements OnInit {
  snippet: ISnippet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ snippet }) => (this.snippet = snippet));
  }

  previousState(): void {
    window.history.back();
  }
}
