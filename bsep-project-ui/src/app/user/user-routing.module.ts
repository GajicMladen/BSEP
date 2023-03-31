import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CsrFormComponent } from './components/csr-form/csr-form.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'csr-request',
  },
  { path: 'csr-request', component: CsrFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
