import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { VerificationService } from '../services/verification.service';
import { passwordRegexValidator } from '../validators/userValidator';

@Component({
  selector: 'app-password-rotation',
  templateUrl: './password-rotation.component.html',
  styleUrls: ['./password-rotation.component.css']
})
export class PasswordRotationComponent {
  
  @ViewChild('child') childComponent: NavbarComponent | undefined;
  
  code: string = '';
  otpValue: string = '';
  isFirstVisible: boolean = false;
  isSecondVisible: boolean = false;
  isThirdVisible: boolean = false;

  passwordRotationForm = new FormGroup({
    old_password: new FormControl('', [Validators.required]),
    new_password: new FormControl('', [Validators.required, passwordRegexValidator]),
    confpass: new FormControl('', [Validators.required])
  }, [])

  constructor(private verificationService: VerificationService, 
    private snackBar: MatSnackBar, 
    private router: Router) { }
  
  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }

  ngOnInit(): void {
    
  }

  resetPassword() {
   
  }

}
