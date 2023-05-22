import { Component, Input, OnInit } from '@angular/core';
import { Realestate } from 'src/app/shared/models/Realestate';
import { RealestatesService } from '../../../../shared/services/realestates.service';
import {
  faCheckCircle
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-realestate-list',
  templateUrl: './realestate-list.component.html',
  styleUrls: ['./realestate-list.component.css']
})
export class RealestateListComponent implements OnInit {

  @Input() realesatets: Realestate[] = [];
  @Input() addingNewRealestates : boolean = false;

  addedRealestaeIDs : { [id:string ]: boolean;} = {};
  faCheckCircle = faCheckCircle;
  constructor(
    private realestateService: RealestatesService
  ) { }
  ngOnInit(): void {
  }

  resetChoice(){
    this.addedRealestaeIDs = {};
  }

  onChange(realestateID:number){

    this.addedRealestaeIDs[realestateID] = !this.addedRealestaeIDs[realestateID];
  }

  getSelectedRealestates(){
    return Object.keys(this.addedRealestaeIDs).filter(key => this.addedRealestaeIDs[key] === true);
  }

}
