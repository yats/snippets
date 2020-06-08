import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbCalendar, NgbDate } from '@ng-bootstrap/ng-bootstrap';

import { ISnippet } from 'app/shared/model/snippet.model';
import { ICategory } from 'app/shared/model/category.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SnippetService } from './snippet.service';
import { CategoryService } from '../category/category.service';

type Query = {
  fromDate: NgbDate;
  toDate: NgbDate;
  fromDateSearch: Number;
  toDateSearch: Number;
  code: string;
  description: string;
  content: string;
  externalCode: string;
  categories: string[];
};
type CategoryResponseType = HttpResponse<ICategory>;
type CategoryArrayResponseType = HttpResponse<ICategory[]>;

@Component({
  selector: 'jhi-snippet',
  templateUrl: './snippet.component.html',
})
export class SnippetComponent implements OnInit, OnDestroy {
  snippets: ISnippet[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  searchQuery: Query = {} as Query;
  results: CategoryArrayResponseType;
  filteredCategories: any[];

  constructor(
    protected snippetService: SnippetService,
    protected categoryService: CategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected calendar: NgbCalendar
  ) {
    this.snippets = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
    this.searchQuery.fromDate = calendar.getNext(calendar.getToday(), 'd', -60);
    this.searchQuery.toDate = calendar.getToday();
    this.filteredCategories = [];
    this.results = new HttpResponse();
  }

  loadAll(): void {
    this.snippetService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISnippet[]>) => this.paginateSnippets(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.snippets = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();

    if (this.searchQuery.fromDate && this.searchQuery.toDate) {
      const fromDate: NgbDate = this.searchQuery.fromDate;
      this.searchQuery.fromDateSearch = new Date(fromDate.year, fromDate.month - 1, fromDate.day, 0, 0, 0, 0).getTime();
      const toDate: NgbDate = this.searchQuery.toDate;
      this.searchQuery.toDateSearch = new Date(toDate.year, toDate.month - 1, toDate.day, 23, 59, 59, 999).getTime();
    } else {
      delete this.searchQuery.fromDateSearch;
      delete this.searchQuery.toDateSearch;
    }
  }

  getAllCategories(event: { query: string }): void {
    const query = event.query;
    this.categoryService.query().subscribe(results => {
      this.filteredCategories = this.categoryService.filterCategory(query, results);
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSnippets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISnippet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSnippets(): void {
    this.eventSubscriber = this.eventManager.subscribe('snippetListModification', () => this.reset());
  }

  delete(snippet: ISnippet): void {
    // const modalRef = this.modalService.open(SnippetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    // modalRef.componentInstance.snippet = snippet;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSnippets(data: ISnippet[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.snippets.push(data[i]);
      }
    }
  }
}
