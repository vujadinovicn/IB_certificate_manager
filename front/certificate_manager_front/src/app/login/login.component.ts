import { CertificateService } from './../services/certificate.service';
import { AuthService } from './../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { VerificationService } from '../services/verification.service';

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

  constructor(private authService: AuthService,
      public snackBar: MatSnackBar,
      private router: Router,
      private certificateService: CertificateService,
      private verificationService: VerificationService) {
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
          // this.certificateService.getAllCertificates().subscribe({
          //   next: (value) => {
          //     this.certificateService.setCertificatesToDisplay(value);
              
          //   },
          //   error: (err) => {
          //     this.snackBar.open("Error wile trying to fetch all certificates.", "", {
          //       duration: 2700, panelClass: ['snack-bar-server-error']
          //    });
          //     console.log("Error wile trying to fetch all certificates.")
          //   },
          // });
          // this.router.navigate(['all-certificates']);
          console.log(this.authService.getUser());
          this.verificationService.sendEmail(this.loginForm.value.email!);
          this.verificationService.sendCause('twofactor');
          this.router.navigate(['/verification-choice']);
        },
        error: (error) => {
          console.log(error);
          console.log("tu")
          console.log(error.error)
          this.snackBar.open("Bad credentials. Please try again!", "", {
            duration: 2700, panelClass: ['snack-bar-server-error']
         });
        },
      });
    }
  }
}
