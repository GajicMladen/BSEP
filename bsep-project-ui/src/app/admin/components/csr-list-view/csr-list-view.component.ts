import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { RequestStatus } from 'src/app/shared/enums/RequestStatus';
import { CertificateService } from 'src/app/shared/services/certificate.service';
import { CsrService } from 'src/app/shared/services/csr.service';
import { Csr } from 'src/app/user/models/Csr';

@Component({
  selector: 'app-csr-list-view',
  templateUrl: './csr-list-view.component.html',
  styleUrls: ['./csr-list-view.component.css'],
})
export class CsrListViewComponent implements OnInit {
  destroy$: Subject<boolean> = new Subject<boolean>();

  csrToShow: Csr[] = [];
  cetrificatesToShow: boolean = false;

  constructor(
    private csrService: CsrService,
    private certificateService: CertificateService,
    private router: Router
  ) {
    router.events.subscribe((val) => {
      // see also
      if (val instanceof NavigationEnd) {
        console.log(this.router.url);

        this.getCsrToShow();
      }
    });
  }

  get RequestStatus() {
    return RequestStatus;
  }

  get numOfPendings() {
    let ret = 0;
    for (let i = 0; i < this.csrToShow.length; i++) {
      if (this.csrToShow[i].status === RequestStatus.PENDING) ret++;
    }
    return ret;
  }

  ngOnInit(): void {
    this.getCsrToShow();
  }

  getCsrToShow() {
    console.log(this.router.url.split('/'));
    let urlEnd = this.router.url.split('/')[2];
    console.log(urlEnd);
    if (urlEnd === 'csr-pending') {
      this.cetrificatesToShow = false;
      this.csrService
        .getPendingCsrs()
        .pipe(takeUntil(this.destroy$))
        .subscribe((res) => {
          this.csrToShow = res;
          console.log(this.csrToShow);
        });
    } else if (urlEnd === 'csr-all') {
      this.cetrificatesToShow = true;
      this.certificateService
        .getAllCertificates()
        .pipe(takeUntil(this.destroy$))
        .subscribe((res) => {
          console.log(res);
          this.csrToShow = res;
        });
    }
  }
}
