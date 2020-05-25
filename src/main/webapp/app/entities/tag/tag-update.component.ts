import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITag, Tag } from 'app/shared/model/tag.model';
import { TagService } from './tag.service';

@Component({
  selector: 'jhi-tag-update',
  templateUrl: './tag-update.component.html',
})
export class TagUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tagCd: [null, [Validators.required]],
    tagDesc: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    lastModifiedDate: [],
    actif: [],
  });

  constructor(protected tagService: TagService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tag }) => {
      if (!tag.id) {
        const today = moment().startOf('day');
        tag.creationDate = today;
        tag.lastModifiedDate = today;
      }

      this.updateForm(tag);
    });
  }

  updateForm(tag: ITag): void {
    this.editForm.patchValue({
      id: tag.id,
      tagCd: tag.tagCd,
      tagDesc: tag.tagDesc,
      creationDate: tag.creationDate ? tag.creationDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedDate: tag.lastModifiedDate ? tag.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      actif: tag.actif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tag = this.createFromForm();
    if (tag.id !== undefined) {
      this.subscribeToSaveResponse(this.tagService.update(tag));
    } else {
      this.subscribeToSaveResponse(this.tagService.create(tag));
    }
  }

  private createFromForm(): ITag {
    return {
      ...new Tag(),
      id: this.editForm.get(['id'])!.value,
      tagCd: this.editForm.get(['tagCd'])!.value,
      tagDesc: this.editForm.get(['tagDesc'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      actif: this.editForm.get(['actif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITag>>): void {
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
}
