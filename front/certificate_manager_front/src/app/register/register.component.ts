import { Component } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { UserDTO, UserService } from '../services/user.service';
import { User } from '../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  // confirmValidParentMatcher = new ConfirmValidParentMatcher();

  regForm = new FormGroup({
    name: new FormControl('', [Validators.required,]),
    surname: new FormControl('', [Validators.required,]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required,]),
    confpass: new FormControl('', [Validators.required,]),
    phonenum: new FormControl('', [Validators.required,]),
  });

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    // markFormControlsTouched(this.registerForm);
  }

  register(formDirective: FormGroupDirective) {
    if (this.regForm.valid) {
      let user: UserDTO = {
        name: this.regForm.value.name!,
        surname: this.regForm.value.surname!,
        email: this.regForm.value.email!,
        password: this.regForm.value.password!,
        telephoneNumber: this.regForm.value.phonenum!,
      }

      this.userService.registerUser(user).subscribe({
        next: (res: User) => {
          console.log('Success');
          resetForm(this.regForm, formDirective);
          this.router.navigate(['validation/mail']);
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
        }
      });
    }
  }
}

export function resetForm(form: FormGroup, fromDirective: FormGroupDirective) {
  fromDirective.resetForm();
  form.reset();
  form.markAsPristine();
  form.markAsUntouched();
  form.updateValueAndValidity();     
}