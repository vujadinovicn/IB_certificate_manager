import { Component } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { ResponseMessageDTO, UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { resetForm } from '../register/register.component';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {


  codeForm = new FormGroup({
    code: new FormControl('', [Validators.required,]),
  });

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    // markFormControlsTouched(this.registerForm);
  }

  verify(formDirective: FormGroupDirective) {
    if (this.codeForm.valid && this.codeForm.value.code) {
      this.userService.verify(this.codeForm.value.code).subscribe({
        next: (res: ResponseMessageDTO) => {
          console.log(res.message);
          resetForm(this.codeForm, formDirective);
          this.router.navigate(['login']);
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
          resetForm(this.codeForm, formDirective);
        }
      });
    } else {
      console.log('greska')
    }
  }

}
