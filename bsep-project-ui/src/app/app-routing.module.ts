import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CsrFormComponent } from './shared/components/csr-form/csr-form.component';
import { CsrListViewComponent } from './admin/components/csr-list-view/csr-list-view.component';

const routes: Routes = [
  {
    path: 'user',
    component: CsrFormComponent,
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
  },
  {
    path: 'admin',
    component: CsrListViewComponent,
    loadChildren: () =>
      import('./admin/admin.module').then((m) => m.AdminModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
