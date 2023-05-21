import { Component, OnInit, ViewChild } from '@angular/core';
import { RealestatesService } from 'src/app/admin/realesates/services/realestates.service';
import { Realestate } from 'src/app/user/models/Realestate';
import { UserService } from '../../services/user.service';
import {
  faCheck,
  faMagnifyingGlass,
  faX,
  faPlus,
  faCheckCircle,
} from '@fortawesome/free-solid-svg-icons';
import { RealestateListComponent } from 'src/app/admin/realesates/components/realestate-list/realestate-list.component';
import {
  MessageService,
  MessageType,
} from 'src/app/shared/services/message.service';
import { Role } from 'src/app/shared/models/Role';
import { User } from 'src/app/shared/models/User';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css'],
})
export class UsersListComponent implements OnInit {
  faCheckCircle = faCheckCircle;
  faPlus = faPlus;

  users: User[] = [];
  realesatets: Realestate[] = [];
  addingNewRealestates: boolean = false;
  selectedUser: User | undefined = undefined;
  isOwner: boolean = false;

  @ViewChild(RealestateListComponent)
  realestateListComponent!: RealestateListComponent;
  constructor(
    private usersService: UserService,
    private realestateService: RealestatesService,
    private messageService: MessageService
  ) {}
  ngOnInit(): void {
    this.restartUsers();
    this.realestateService.getAll().subscribe((data) => {
      this.realesatets = data;
    });
  }

  restartUsers() {
    this.usersService.getAll().subscribe((data) => {
      this.users = data;
    });
  }

  selectUser(user: User) {
    this.selectedUser = user;
    this.isOwner = user.roles.includes(Role.OWNER);
    this.addingNewRealestates = false;
    this.realestateService.getForUser(user.id).subscribe((data) => {
      this.realesatets = data;
    });
  }

  addNewRealestates(user: User) {
    this.selectedUser = user;
    this.realestateListComponent.resetChoice();
    this.realestateService.getAll().subscribe((data) => {
      this.realesatets = data;
    });
    this.addingNewRealestates = true;
  }

  updateRealestates() {
    let rsIDs = this.realestateListComponent.getSelectedRealestates();
    this.realestateService
      .updateRealestateForUser(this.selectedUser!.id, rsIDs)
      .subscribe((data) => {
        if (data) {
          this.addingNewRealestates = false;
          this.realestateListComponent.resetChoice();
          this.selectUser(this.selectedUser!);
          this.messageService.showMessage(
            'Uspesno ste azurirali nekretnine korisnika',
            MessageType.SUCCESS
          );
        }
      });
  }

  changeUserRole() {
    this.usersService
      .changeUserRole(this.selectedUser!.id)
      .subscribe((data) => {
        if (data)
          this.messageService.showMessage(
            'Uspesno ste promenili ulogu korisnika',
            MessageType.SUCCESS
          );
        this.restartUsers();
      });
    this.isOwner = !this.isOwner;
  }
}
