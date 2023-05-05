import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../services/user.service';

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

  constructor() { }
  
  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }

  ngOnInit(): void {
    
  }

  public otpConfig: NgxOtpInputConfig = {
    otpLength: 6,
    numericInputMode: true
  };

  //ova funkcija se poziva svaki put kad se doda novi broj, na kraju kad kolektujes vrednosti dobavis samo this.otpvalue
  onOtpComplete(event: any) {
    this.otpValue = event;
  }

  resetPassword() {
   
  }

}
