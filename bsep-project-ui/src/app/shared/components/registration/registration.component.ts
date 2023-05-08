import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { MessageService, MessageType } from '../../services/message.service';
import { RegistrationService } from '../../services/registration.service';
import { RegistrationData } from '../../models/RegistrationData';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit, OnDestroy {
  destroy$: Subject<boolean> = new Subject<boolean>();
  form: FormGroup = this.createRegistrationForm();

  constructor(
    private messageService: MessageService,
    private registrationService: RegistrationService
  ) {}

  ngOnInit(): void {}

  createRegistrationForm(): FormGroup {
    return new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.pattern('^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'),
      ]),
      name: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      role: new FormControl('ROLE_USER', Validators.required),
    });
  }

  registerUser(): void {
    if (this.form.invalid) {
      this.messageService.showMessage(
        'Registraciona forma nije adekvatno popunjena!',
        MessageType.ERROR
      );
    } else {
      let data: RegistrationData = this.form.getRawValue();
      this.registrationService
        .sendRegistrationRequest(data)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            this.messageService.showMessage(
              'Registracija uspešna! E-mail sa daljim instrukcijama je poslat korisniku!',
              MessageType.SUCCESS
            );
          },
          error: (err) => {
            console.log(err);
          },
        });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }
}
