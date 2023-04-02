import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrListViewComponent } from './components/csr-list-view/csr-list-view.component';
import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CsrItemComponent } from './components/csr-item/csr-item.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [CsrListViewComponent, CsrItemComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FontAwesomeModule,
  ],
})
export class AdminModule {}
