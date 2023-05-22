import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OwnerRoutingModule {}
