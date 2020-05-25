import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISnippet } from 'app/shared/model/snippet.model';
import { SnippetService } from './snippet.service';

@Component({
  templateUrl: './snippet-delete-dialog.component.html',
})
export class SnippetDeleteDialogComponent {
  snippet?: ISnippet;

  constructor(protected snippetService: SnippetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.snippetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('snippetListModification');
      this.activeModal.close();
    });
  }
}
