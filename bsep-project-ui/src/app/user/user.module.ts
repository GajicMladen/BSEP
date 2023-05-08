import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrFormComponent } from '../shared/components/csr-form/csr-form.component';
import { UserRoutingModule } from './user-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ChangeCredentialsComponent } from './components/change-credentials/change-credentials.component';

@NgModule({
  declarations: [CsrFormComponent, ChangeCredentialsComponent],
  imports: [
    CommonModule,
    UserRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
})
export class UserModule {}
