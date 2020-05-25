import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISnippet, Snippet } from 'app/shared/model/snippet.model';
import { SnippetService } from './snippet.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';

type SelectableEntity = ICategory | ITag;

@Component({
  selector: 'jhi-snippet-update',
  templateUrl: './snippet-update.component.html',
})
export class SnippetUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  tags: ITag[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
    description: [null, [Validators.required]],
    textCode: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    lastModifiedDate: [],
    actif: [],
    gitlabExternalCode: [],
    categoryCd: [],
    tagCd: [],
  });

  constructor(
    protected snippetService: SnippetService,
    protected categoryService: CategoryService,
    protected tagService: TagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ snippet }) => {
      if (!snippet.id) {
        const today = moment().startOf('day');
        snippet.creationDate = today;
        snippet.lastModifiedDate = today;
      }

      this.updateForm(snippet);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.tagService.query().subscribe((res: HttpResponse<ITag[]>) => (this.tags = res.body || []));
    });
  }

  updateForm(snippet: ISnippet): void {
    this.editForm.patchValue({
      id: snippet.id,
      code: snippet.code,
      libelle: snippet.libelle,
      description: snippet.description,
      textCode: snippet.textCode,
      creationDate: snippet.creationDate ? snippet.creationDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedDate: snippet.lastModifiedDate ? snippet.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      actif: snippet.actif,
      gitlabExternalCode: snippet.gitlabExternalCode,
      categoryCd: snippet.categoryCd,
      tagCd: snippet.tagCd,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const snippet = this.createFromForm();
    if (snippet.id !== undefined) {
      this.subscribeToSaveResponse(this.snippetService.update(snippet));
    } else {
      this.subscribeToSaveResponse(this.snippetService.create(snippet));
    }
  }

  private createFromForm(): ISnippet {
    return {
      ...new Snippet(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      textCode: this.editForm.get(['textCode'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      actif: this.editForm.get(['actif'])!.value,
      gitlabExternalCode: this.editForm.get(['gitlabExternalCode'])!.value,
      categoryCd: this.editForm.get(['categoryCd'])!.value,
      tagCd: this.editForm.get(['tagCd'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISnippet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
