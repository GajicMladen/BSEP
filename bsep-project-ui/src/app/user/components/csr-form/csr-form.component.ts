import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-crs-form',
  templateUrl: './csr-form.component.html',
  styleUrls: ['./csr-form.component.css'],
})
export class CsrFormComponent implements OnInit {
  public SIGNING_ALGORITHMS = ['SHA-1/RSA', 'SHA-256/RSA', 'SHA-512/RSA'];
  csrForm: FormGroup = this.createCsrForm();
  constructor() {}

  ngOnInit(): void {}

  createCsrForm(): FormGroup {
    return new FormGroup({
      commonName: new FormControl('', Validators.required),
      organizationName: new FormControl('', Validators.required),
      organizationalUnit: new FormControl('', Validators.required),
      locality: new FormControl('', Validators.required),
      state: new FormControl('', Validators.required),
      country: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
    });
  }
}
