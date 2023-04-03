import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrListViewComponent } from './components/csr-list-view/csr-list-view.component';
import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CsrItemComponent } from './components/csr-item/csr-item.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CertificateItemComponent } from './components/certificate-item/certificate-item.component';

@NgModule({
  declarations: [CsrListViewComponent, CsrItemComponent, CertificateItemComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FontAwesomeModule,
  ],
})
export class AdminModule {}
