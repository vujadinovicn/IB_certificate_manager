import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { SmsCodeService } from '../services/sms-code.service';
import { UserDTO } from '../services/user.service';

@Component({
  selector: 'app-sms-code',
  templateUrl: './sms-code.component.html',
  styleUrls: ['./sms-code.component.css']
})
export class SmsCodeComponent implements OnInit{

  user: UserDTO = {} as UserDTO;

  @ViewChild('child') childComponent: NavbarComponent | undefined;

  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }
  
  otpValue: any;

  constructor(private smsCodeService: SmsCodeService, private router: Router){

  }
  ngOnInit(): void {
    this.smsCodeService.recieveUserDTO().subscribe((res: any) =>{
      this.user = res;
    });
  }

  public otpConfig: NgxOtpInputConfig = {
    otpLength: 6,
    numericInputMode: true
  };

  onOtpComplete(event: any) {
    this.otpValue = event;
  }

  sendSMS(){ 
    this.smsCodeService.activateBySms(
      {
        email: this.user.email,
        code: this.otpValue
      }
    ).subscribe({
      next: (res: any) => {
        console.log(res.message);
        this.router.navigate(['login']);
      },
      error: (err: any) => {
        console.log(err);
      }
    })
  }

  sendNewSMS(){
    this.smsCodeService.sendNewSms(this.user).subscribe({
      next: (res: any) => {
        console.log(res.message);  
      },
      error: (err: any) => {
        console.log(err);
      }
    })
  }


  // verifikovati putem endpointa activate/{activationId}

}
