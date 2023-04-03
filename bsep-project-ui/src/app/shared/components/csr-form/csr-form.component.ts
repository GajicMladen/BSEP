import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import {
  countryCodeValidator,
  emailValidator,
} from 'src/app/shared/validators/validators';
import { CsrService } from '../../services/csr.service';
import {
  MessageService,
  MessageType,
} from 'src/app/shared/services/message.service';
import { Csr } from '../../../user/models/Csr';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-csr-form',
  templateUrl: './csr-form.component.html',
  styleUrls: ['./csr-form.component.css'],
})
export class CsrFormComponent implements OnInit {
  destroy$: Subject<boolean> = new Subject<boolean>();

  public SIGNING_ALGORITHMS = ['SHA-1/RSA', 'SHA-256/RSA', 'SHA-512/RSA'];
  public KEY_SIZES = [1024, 2048, 3072, 4096];

  csrForm: FormGroup = this.createCsrForm({});

  minDate = new Date();

  @Input() previewMode: boolean = false;
  @Input() certificateMode: boolean = false;

  constructor(
    private csrService: CsrService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    if (this.previewMode) this.disableCsrFields();
  }

  createCsrForm(data: any): FormGroup {
    return new FormGroup({
      commonName: new FormControl(data.commonName || '', Validators.required),
      organizationName: new FormControl(
        data.organizationName || '',
        Validators.required
      ),
      organizationalUnit: new FormControl(
        data.organizationalUnit || '',
        Validators.required
      ),
      locality: new FormControl(data.locality || '', Validators.required),
      state: new FormControl(data.state || '', Validators.required),
      country: new FormControl(data.country || '', [
        Validators.required,
        countryCodeValidator,
      ]),
      email: new FormControl(data.email || '', [
        Validators.required,
        emailValidator,
      ]),
      algorithm: new FormControl(
        data.algorithm || 'SHA-512/RSA',
        Validators.required
      ),
      intermediateCertificate: new FormControl(
        data.intermediateCertificate || 'Sertifikat za korisnike',
        Validators.required
      ),
    });
  }

  createCertificateForm(data: any): FormGroup {
    let ret = this.createCsrForm(data);
    ret.addControl('version', new FormControl(data.version || 3));
    ret.addControl('startDate', new FormControl(data.startTime || ''));
    ret.addControl('endDate', new FormControl(data.endTime || ''));
    ret.addControl('keySize', new FormControl(data.keySize || 2048));
    ret.addControl(
      'alias',
      new FormControl(data.alias || '', Validators.required)
    );
    ret.addControl('extensions', new FormArray(data.extensions || []));
    return ret;
  }

  disableCsrFields() {
    this.csrForm.get('commonName')!.disable();
    this.csrForm.get('organizationName')!.disable();
    this.csrForm.get('organizationalUnit')!.disable();
    this.csrForm.get('locality')!.disable();
    this.csrForm.get('state')!.disable();
    this.csrForm.get('country')!.disable();
    this.csrForm.get('email')!.disable();
    this.csrForm.get('algorithm')!.disable();
  }

  onSubmitClick() {
    let csr: Csr = this.csrForm.getRawValue();
    if (this.certificateMode) {
      this.csrService
        .approveCsr(csr)
        .pipe(takeUntil(this.destroy$))
        .subscribe((res) => {
          this.messageService.showMessage('Success!', MessageType.SUCCESS);
        });
    } else {
      this.csrService
        .postCsr(csr)
        .pipe(takeUntil(this.destroy$))
        .subscribe((res) => {
          this.messageService.showMessage('Success!', MessageType.SUCCESS);
        });
    }
  }
}
