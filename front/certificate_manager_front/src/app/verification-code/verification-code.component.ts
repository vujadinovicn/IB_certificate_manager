import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { NgxOtpInputConfig } from 'ngx-otp-input';
import { NavbarComponent } from '../navbar/navbar.component';
import { VerificationService } from '../services/verification.service';
import { UserDTO } from '../services/user.service';

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css']
})
export class VerificationCodeComponent implements OnInit{

  user: UserDTO = {} as UserDTO;
  // sendCodeVia: string = '';

  @ViewChild('child') childComponent: NavbarComponent | undefined;

  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }
  
  otpValue: any;

  constructor(private verificationService: VerificationService, private router: Router, private route: ActivatedRoute){

  }
  ngOnInit(): void {
    // this.sendCodeVia = this.route.snapshot.paramMap.get('type')!;
    this.verificationService.recieveUserDTO().subscribe((res: any) =>{
      this.user = res;
    });
  }

  public otpConfig: NgxOtpInputConfig = {
    otpLength: 6,
    // numericInputMode: false
    pattern: /^[a-zA-Z0-9_.-]$/
  };

  onOtpComplete(event: any) {
    this.otpValue = event;
  }

  verifyRegistration(){ 
    this.verificationService.verifyRegistration(this.otpValue).subscribe({
      next: (res: any) => {
        console.log(res.message);
        this.router.navigate(['login']);
      },
      error: (err: any) => {
        console.log(err);
      }
    })
  }

}
