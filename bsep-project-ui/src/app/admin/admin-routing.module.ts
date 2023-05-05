import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CsrListViewComponent } from './components/csr-list-view/csr-list-view.component';
import { UsersListComponent } from './users/components/users-list/users-list.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'csr-pending',
  },
  {
    path: 'csr-pending',
    component: CsrListViewComponent,
  },
  {
    path: 'csr-all',
    component: CsrListViewComponent,
  },
  {
    path: 'users-all',
    component: UsersListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
