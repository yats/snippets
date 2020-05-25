import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SnippetsTestModule } from '../../../test.module';
import { SnippetDetailComponent } from 'app/entities/snippet/snippet-detail.component';
import { Snippet } from 'app/shared/model/snippet.model';

describe('Component Tests', () => {
  describe('Snippet Management Detail Component', () => {
    let comp: SnippetDetailComponent;
    let fixture: ComponentFixture<SnippetDetailComponent>;
    const route = ({ data: of({ snippet: new Snippet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnippetsTestModule],
        declarations: [SnippetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SnippetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SnippetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load snippet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.snippet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
