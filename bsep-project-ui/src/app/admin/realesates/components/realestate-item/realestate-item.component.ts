import { Component, Input, OnInit } from '@angular/core';
import { Realestate } from 'src/app/shared/models/Realestate';
import {
  faCheck,
  faMagnifyingGlass,
  faX,
  faPlus,
  faHouse
} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-realestate-item',
  templateUrl: './realestate-item.component.html',
  styleUrls: ['./realestate-item.component.css']
})
export class RealestateItemComponent implements OnInit {

  @Input() realestate:Realestate | null = null;


  faCheck =faCheck
  faMagnifyingGlass =faMagnifyingGlass
  faX =faX
  faPlus =faPlus
  faHouse = faHouse
  constructor() { }

  ngOnInit(): void {
  }

}
