import { Component } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { ResponseMessageDTO, UserDTO, UserService } from '../services/user.service';
import { User } from '../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { VerificationCodeComponent } from '../verification-code/verification-code.component';
import { VerificationService } from '../services/verification.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { nameRegexValidator, passwordRegexValidator, surnameRegexValidator } from '../validators/userValidator';
import { passwordMatcher } from '../validators/passwordMatch';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  // confirmValidParentMatcher = new ConfirmValidParentMatcher();

  isFirstVisible: boolean = false;
  isSecondVisible: boolean = false;
  
  regForm = new FormGroup({
    name: new FormControl('', [Validators.required, nameRegexValidator]),
    surname: new FormControl('', [Validators.required, surnameRegexValidator]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, passwordRegexValidator]),
    confpass: new FormControl('', [Validators.required, passwordRegexValidator]),
    phonenum: new FormControl('', [Validators.required,]),
  }, [passwordMatcher("password", "confpass")]);

  constructor(private snackBar: MatSnackBar, 
    private userService: UserService, 
    private verificationService: VerificationService, private router: Router) { }

  ngOnInit(): void {
    // markFormControlsTouched(this.registerForm);
  }

  register(formDirective: FormGroupDirective) {
    if (this.regForm.valid) {
      let user: UserDTO = {
        name: this.regForm.value.name!,
        lastname: this.regForm.value.surname!,
        email: this.regForm.value.email!,
        password: this.regForm.value.password!,
        phoneNumber: this.regForm.value.phonenum!,
      }

      this.userService.registerUser(user).subscribe({
        next: (res: ResponseMessageDTO) => {
          this.snackBar.open(res.message, "", {
            duration: 2700, panelClass: ['snack-bar-success']
        });
          resetForm(this.regForm, formDirective);
          this.verificationService.sendUserDTO(user);
          this.verificationService.sendCause('registration');
          this.router.navigate(['verification-choice']);
        },
        error: (err: HttpErrorResponse) => {
          this.snackBar.open(err.error, "", {
            duration: 2700, panelClass: ['snack-bar-server-error']
         });
        }
      });
    } else 
      this.snackBar.open("Check your inputs again!", "", {
        duration: 2700,  panelClass: ['snack-bar-front-error'] 
    });
  }
}

export function resetForm(form: FormGroup, fromDirective: FormGroupDirective) {
  fromDirective.resetForm();
  form.reset();
  form.markAsPristine();
  form.markAsUntouched();
  form.updateValueAndValidity();     
}