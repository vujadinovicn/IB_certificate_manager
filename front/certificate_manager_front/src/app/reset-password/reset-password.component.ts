import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../services/user.service';
import { VerificationService } from '../services/verification.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  @ViewChild('child') childComponent: NavbarComponent | undefined;
  
  code: string = '';
  otpValue: string = '';
  isFirstVisible: boolean = false;
  isSecondVisible: boolean = false;

  resetPasswordForm = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confpass: new FormControl('', [Validators.required])
  }, [])

  constructor(private verificationService: VerificationService, private router: Router) { }
  
  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }

  ngOnInit(): void {
    
  }

  public otpConfig: NgxOtpInputConfig = {
    otpLength: 6,
    pattern: /^[a-zA-Z0-9_.-]$/
  };

  //ova funkcija se poziva svaki put kad se doda novi broj, na kraju kad kolektujes vrednosti dobavis samo this.otpvalue
  onOtpComplete(event: any) {
    this.otpValue = event;
  }

  resetPassword() {
    if (this.resetPasswordForm.valid){
      this.verificationService.resetPassword({
        newPassword: this.resetPasswordForm.value.password!,
        code: this.otpValue
      }).subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['login']);
        },
        error: (err: any) => {
          console.log(err);
        }
      })
    } else 
      console.log("err");
  }

}
