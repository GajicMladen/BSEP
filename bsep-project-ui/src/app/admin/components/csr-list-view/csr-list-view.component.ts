import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { CsrService } from 'src/app/shared/services/csr.service';
import { Csr } from 'src/app/user/models/Csr';

@Component({
  selector: 'app-csr-list-view',
  templateUrl: './csr-list-view.component.html',
  styleUrls: ['./csr-list-view.component.css'],
})
export class CsrListViewComponent implements OnInit {
  destroy$: Subject<boolean> = new Subject<boolean>();

  pendingCsrs: Csr[] = [];

  constructor(private csrService: CsrService) {}

  ngOnInit(): void {
    this.csrService
      .getPendingCsrs()
      .pipe(takeUntil(this.destroy$))
      .subscribe((res) => (this.pendingCsrs = res));
  }
}
