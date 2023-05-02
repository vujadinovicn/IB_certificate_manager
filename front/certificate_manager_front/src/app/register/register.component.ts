import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';


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

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // markFormControlsTouched(this.registerForm);
  }

}
