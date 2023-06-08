import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CsrFormComponent } from '../shared/components/csr-form/csr-form.component';
import { ChangeCredentialsComponent } from './components/change-credentials/change-credentials.component';
import { LogsTableComponent } from '../shared/components/logs-table/logs-table.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'csr-request',
  },
  { path: 'csr-request', component: CsrFormComponent },
  { path: 'change-credentials', component: ChangeCredentialsComponent },
  { path:'logs', component: LogsTableComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
