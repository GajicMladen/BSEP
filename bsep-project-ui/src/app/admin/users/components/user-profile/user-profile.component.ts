import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/user/models/User';
import {
  faCheck,
  faMagnifyingGlass,
  faX,
  faPlus
} from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../../services/user.service';
import { MessageService, MessageType } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  @Input() user : User | null = null; 

  @Output() userDeletedEmiter: EventEmitter<number> =new EventEmitter();
  constructor(
    private userService : UserService,
    private messageService: MessageService
  ) { }

  faMagnifyingGlass = faMagnifyingGlass;
  faPlus = faPlus;
  faX = faX;

  ngOnInit(): void {
  }

  openPreviewDialog(){

  }

  deletUser(userId:number){
    this.userService.deleteUser(userId).subscribe(
      data =>{
        if(data){
          this.userDeletedEmiter.emit();
          this.messageService.showMessage("Uspesno ste obrisali korisnika",MessageType.SUCCESS);
        }
      }
    )
  }
}
