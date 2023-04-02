import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CsrListViewComponent } from './components/csr-list-view/csr-list-view.component';

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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
