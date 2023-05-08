import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CsrFormComponent } from './shared/components/csr-form/csr-form.component';
import { UsersListComponent } from './admin/users/components/users-list/users-list.component';
import { LoginComponent } from './shared/components/login/login.component';
import { RegistrationComponent } from './shared/components/registration/registration.component';
import { ChangeCredentialsComponent } from './user/components/change-credentials/change-credentials.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'user',
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin.module').then((m) => m.AdminModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
