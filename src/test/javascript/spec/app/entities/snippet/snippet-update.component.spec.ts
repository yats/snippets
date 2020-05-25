import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SnippetsTestModule } from '../../../test.module';
import { SnippetUpdateComponent } from 'app/entities/snippet/snippet-update.component';
import { SnippetService } from 'app/entities/snippet/snippet.service';
import { Snippet } from 'app/shared/model/snippet.model';

describe('Component Tests', () => {
  describe('Snippet Management Update Component', () => {
    let comp: SnippetUpdateComponent;
    let fixture: ComponentFixture<SnippetUpdateComponent>;
    let service: SnippetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnippetsTestModule],
        declarations: [SnippetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SnippetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SnippetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SnippetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Snippet(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Snippet();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
