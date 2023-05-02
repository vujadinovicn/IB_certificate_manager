import { Component } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { ResponseMessageDTO, UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { resetForm } from '../register/register.component';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-mail-verification',
  templateUrl: './mail-verification.component.html',
  styleUrls: ['./mail-verification.component.css']
})
export class MailVerificationComponent {


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
