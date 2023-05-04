import { Component, ViewChild } from '@angular/core';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { SmsCodeService } from '../services/sms-code.service';

@Component({
  selector: 'app-sms-code',
  templateUrl: './sms-code.component.html',
  styleUrls: ['./sms-code.component.css']
})
export class SmsCodeComponent {

  @ViewChild('child') childComponent: NavbarComponent | undefined;

  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }
  
  otpValue: any;

  constructor(private smsCodeService: SmsCodeService){

  }

  public otpConfig: NgxOtpInputConfig = {
    otpLength: 6,
    numericInputMode: true,
    classList: {
      input: 'my-super-class',
      inputFilled: 'my-super-filled-class',
      inputDisabled: 'my-super-disable-class',
      inputSuccess: 'my-super-success-class',
      inputError: 'my-super-error-class',
    },
  };

  onOtpComplete(event: any) {
    console.log('OTP code entered:', event.code);
    this.otpValue = event;
    // do something with the OTP code
  }

  submit(){
    let o = {
      username: "neca", 
      code: "333333"
    }
    this.smsCodeService.activateBySms(
      o
    ).subscribe((res: any) => {
      console.log(res);
      console.log(res.status);
    }
    )
  }

}
