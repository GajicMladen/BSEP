import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {
  faCheck,
  faMagnifyingGlass,
  faX,
} from '@fortawesome/free-solid-svg-icons';
import { Subject, takeUntil } from 'rxjs';
import { CsrFormComponent } from 'src/app/shared/components/csr-form/csr-form.component';
import { Csr } from 'src/app/user/models/Csr';

@Component({
  selector: 'app-csr-item',
  templateUrl: './csr-item.component.html',
  styleUrls: ['./csr-item.component.css'],
})
export class CsrItemComponent implements OnInit {
  destroy$: Subject<boolean> = new Subject<boolean>();

  faMagnifyingGlass = faMagnifyingGlass;
  faCheck = faCheck;
  faX = faX;

  @Input() csrRequest!: Csr;

  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {}

  openPreviewDialog(certificateMode: boolean): void {
    const dialogRef = this.dialog.open(CsrFormComponent);
    let comp = dialogRef.componentInstance;

    if (certificateMode)
      comp.csrForm = comp.createCertificateForm(this.csrRequest);
    else comp.csrForm = comp.createCsrForm(this.csrRequest);
    comp.previewMode = true;
    comp.certificateMode = certificateMode;

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.destroy$))
      .subscribe((res) => {
        console.log(res);
      });
  }
}
