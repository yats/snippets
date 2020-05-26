import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-snippets-management',
  templateUrl: './snippets-management.component.html',
  styleUrls: ['snippets-management.component.scss'],
})
export class SnippetsManagementComponent implements OnInit {
  message: string;

  constructor() {
    this.message = 'SnippetsManagementComponent message';
  }

  ngOnInit(): void {}
}
