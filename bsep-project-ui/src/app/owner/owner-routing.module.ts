import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LogsTableComponent } from '../shared/components/logs-table/logs-table.component';
import { OwnerDevicesComponent } from './components/owner-devices/owner-devices.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'devices',
  },
  {
    path:'devices',
    component: OwnerDevicesComponent
  },
  {
    path:'logs',
    component:LogsTableComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OwnerRoutingModule {}
