import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrFormComponent } from '../shared/components/csr-form/csr-form.component';
import { UserRoutingModule } from './user-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [CsrFormComponent],
  imports: [
    CommonModule,
    UserRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
})
export class UserModule {}
