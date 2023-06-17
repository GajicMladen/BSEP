import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrListViewComponent } from './components/csr-list-view/csr-list-view.component';
import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CsrItemComponent } from './components/csr-item/csr-item.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CertificateItemComponent } from './components/certificate-item/certificate-item.component';
import { UsersListComponent } from './users/components/users-list/users-list.component';
import { UserProfileComponent } from './users/components/user-profile/user-profile.component';
import { RealestateListComponent } from './realesates/components/realestate-list/realestate-list.component';
import { RealestateItemComponent } from './realesates/components/realestate-item/realestate-item.component';
import { LoginLogsComponent } from './components/login-logs/login-logs.component';

@NgModule({
  declarations: [CsrListViewComponent, CsrItemComponent, CertificateItemComponent, UsersListComponent, UserProfileComponent, RealestateListComponent, RealestateItemComponent, LoginLogsComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FontAwesomeModule,
  ],
})
export class AdminModule {}
