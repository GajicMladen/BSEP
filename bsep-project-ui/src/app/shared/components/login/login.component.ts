import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from '../../services/login.service';
import { LoginData } from '../../models/LoginData';
import { LoginResponseData } from '../../models/LoginResponseData';
import { MessageService, MessageType } from '../../services/message.service';
import { User } from '../../models/User';
import { Role } from '../../models/Role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  destroy$: Subject<boolean> = new Subject<boolean>();
  form: FormGroup = this.createLoginForm();

  constructor(
    private loginService: LoginService,
    private messageService: MessageService
  ) {
    if (this.loginService.isTokenPresent) {
      // this.redirectLoggedUser();
      console.log('Token Present!');
    }
  }

  ngOnInit(): void {}

  createLoginForm(): FormGroup {
    return new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      pin: new FormControl('', Validators.required),
    });
  }

  loginRequest() {
    let data: LoginData = this.form.getRawValue();
    this.loginService
      .sendLoginRequest(data)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (res: LoginResponseData) => {
          this.loginService.setUserData(res);
          console.log(this.loginService.user);
        },
        error: (err) => {
          this.messageService.showMessage(err.error.message, MessageType.ERROR);
        },
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }
}
