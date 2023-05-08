import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { VerificationService } from '../services/verification.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit{

  @ViewChild('child') childComponent: NavbarComponent | undefined;

  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }
  
  forgotPasswordForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email])
  })

  constructor(private snackBar: MatSnackBar, 
    private verificationService: VerificationService, private router: Router) { }

  ngOnInit(): void {
  }

  sendVerificationCode() {
    if (this.forgotPasswordForm.valid)
      this.router.navigate(['verification', {email: this.forgotPasswordForm.value.email}]);
    else 
      this.snackBar.open("Check your inputs again!", "", {
        duration: 2700,  panelClass: ['snack-bar-front-error'] 
    });
  }
}