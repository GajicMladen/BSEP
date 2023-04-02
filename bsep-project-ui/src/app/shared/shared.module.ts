import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [NavbarComponent, NavbarComponent],
  imports: [CommonModule, MaterialModule, RouterModule, FontAwesomeModule],
  exports: [NavbarComponent],
})
export class SharedModule {}
