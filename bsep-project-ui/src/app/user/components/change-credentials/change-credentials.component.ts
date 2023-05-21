import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import {
  MessageService,
  MessageType,
} from 'src/app/shared/services/message.service';
import { RegistrationService } from 'src/app/shared/services/registration.service';
import { passwordCharactersValidator } from 'src/app/shared/validators/validators';
import { CredentialsDTO } from '../../models/CredentialsDTO';
import { LoginService } from 'src/app/shared/services/login.service';

@Component({
  selector: 'app-change-credentials',
  templateUrl: './change-credentials.component.html',
  styleUrls: ['./change-credentials.component.css'],
})
export class ChangeCredentialsComponent implements OnInit, OnDestroy {
  destroy$: Subject<boolean> = new Subject<boolean>();
  form: FormGroup = this.createCredentialsForm();

  constructor(
    private userService: LoginService,
    private registrationService: RegistrationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  createCredentialsForm(): FormGroup {
    const passwordValidators = [
      passwordCharactersValidator,
      Validators.minLength(12),
      Validators.maxLength(64),
    ];

    return new FormGroup({
      oldPassword: new FormControl('', Validators.required),
      oldPin: new FormControl('', Validators.required),
      newPassword: new FormControl('', passwordValidators),
      newPin: new FormControl('', [Validators.pattern(/^\d{4}$/)]),
    });
  }

  get newPasswordErrorText(): string {
    let ctrl = this.form.get('newPassword') as FormControl;

    if (ctrl.hasError('minlength')) return 'Minimalna dužina lozinke je 12!';
    else if (ctrl.hasError('maxlength'))
      return 'Maksimalna dužina lozinke je 64!';
    else if (ctrl.hasError('lowerMissingError'))
      return 'Lozinka mora da sadrži bar jedno malo slovo!';
    else if (ctrl.hasError('upperMissingError'))
      return 'Lozinka mora da sadrži bar jedno veliko slovo!';
    else if (ctrl.hasError('numberMissingError'))
      return 'Lozinka mora da sadrži bar jedan broj!';
    else if (ctrl.hasError('specialMissingError'))
      return 'Lozinka mora da sadrži bar jedno od sledećih znakova: !@#$%^&*_=+-/!';
    else return '';
  }

  sendChangeRequest() {
    let data: CredentialsDTO = this.form.getRawValue();
    data.email = this.userService.user!.email;
    this.registrationService
      .sendChangeCredentialsRequest(data)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.messageService.showMessage(
            'Promena uspešna!',
            MessageType.SUCCESS
          );
          this.form.patchValue(this.createCredentialsForm().getRawValue());
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
