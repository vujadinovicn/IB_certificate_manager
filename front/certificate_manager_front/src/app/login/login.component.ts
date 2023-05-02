import { AuthService } from './../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  isVisible : boolean = false;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  constructor(private authService: AuthService, public snackBar: MatSnackBar){
    
  }
  
  ngOnInit(): void {

  }

  login(){
    const credentials = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    };

    if (this.loginForm.valid) {
      this.authService.login(credentials).subscribe({
        next: (result) => {
          localStorage.setItem('user', JSON.stringify(result.accessToken));
          // localStorage.setItem('refreshToken', JSON.stringify(result.refreshToken));
          this.authService.setUser();
          console.log("USPEH")
          console.log(this.authService.getUserFromStorage());
        },
        error: (error) => {
          console.log(error);
          console.log("tu")
          this.snackBar.open("Bad credentials. Please try again!", "", {
            duration: 2000,
         });
        },
      });
    }
  }
}
