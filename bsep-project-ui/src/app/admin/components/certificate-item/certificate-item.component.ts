import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {
  faCheck,
  faMagnifyingGlass,
  faX,
} from '@fortawesome/free-solid-svg-icons';
import { Subject, takeUntil } from 'rxjs';
import { CsrFormComponent } from 'src/app/shared/components/csr-form/csr-form.component';
import { RequestStatus } from 'src/app/shared/enums/RequestStatus';
import { CertificateService } from 'src/app/shared/services/certificate.service';
import { CsrService } from 'src/app/shared/services/csr.service';
import {
  MessageService,
  MessageType,
} from 'src/app/shared/services/message.service';
import { Csr } from 'src/app/user/models/Csr';
@Component({
  selector: 'app-certificate-item',
  templateUrl: './certificate-item.component.html',
  styleUrls: ['./certificate-item.component.css'],
})
export class CertificateItemComponent implements OnInit {
  destroy$: Subject<boolean> = new Subject<boolean>();

  faMagnifyingGlass = faMagnifyingGlass;
  faCheck = faCheck;
  faX = faX;

  @Input() csrRequest!: Csr;

  @Output() csrChanged: EventEmitter<any> = new EventEmitter();

  constructor(
    private dialog: MatDialog,
    private certificateService: CertificateService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  get RequestStatus() {
    return RequestStatus;
  }

  openPreviewDialog(certificateMode: boolean): void {
    const dialogRef = this.dialog.open(CsrFormComponent);
    let comp = dialogRef.componentInstance;

    if (certificateMode)
      comp.csrForm = comp.createCertificateForm(this.csrRequest);
    else comp.csrForm = comp.createCsrForm(this.csrRequest);
    comp.csrForm.disable();
    comp.previewMode = true;
    comp.certificateMode = certificateMode;

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.destroy$))
      .subscribe((res) => {
        console.log(res);
      });
  }

  validateCsr(alias: string) {
    this.certificateService
      .validateCertificate(alias)
      .pipe(takeUntil(this.destroy$))
      .subscribe((res) => {
        if (res) {
          this.messageService.showMessage(
            'Sertifikat je validan!',
            MessageType.SUCCESS
          );
        } else {
          this.messageService.showMessage(
            'Sertifikat nije validan!',
            MessageType.ERROR
          );
        }
      });
  }

  cancelCsr(alias: string) {
    this.certificateService
      .cancelCertificate(alias)
      .pipe(takeUntil(this.destroy$))
      .subscribe((res) => {
        console.log(res);
        this.csrChanged.emit(res);
      });
  }
}
