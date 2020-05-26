import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-page-one',
  templateUrl: './page-one.component.html',
  styleUrls: ['page-one.component.scss'],
})
export class PageOneComponent implements OnInit {
  message: string;

  constructor() {
    this.message = 'PageOneComponent message';
  }

  ngOnInit(): void {}
}
