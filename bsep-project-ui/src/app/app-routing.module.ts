import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CsrFormComponent } from './user/components/csr-form/csr-form.component';

const routes: Routes = [
  {
    path: 'user',
    component: CsrFormComponent,
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
