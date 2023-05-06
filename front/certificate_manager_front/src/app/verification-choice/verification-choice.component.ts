import { Component, OnInit } from '@angular/core';
import { AuthService, User } from '../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDTO } from '../services/user.service';
import { VerificationService } from '../services/verification.service';

@Component({
  selector: 'app-verification-choice',
  templateUrl: './verification-choice.component.html',
  styleUrls: ['./verification-choice.component.css']
})
export class VerificationChoiceComponent implements OnInit{

  userDTO: UserDTO = {} as UserDTO;
  emailForReset: string = '';

  constructor(private router: Router, private route: ActivatedRoute, public snackBar: MatSnackBar, private verificationService: VerificationService){
    
  }
  
  ngOnInit(): void {
    this.emailForReset = this.route.snapshot.paramMap.get('email')!;
    this.verificationService.recieveUserDTO().subscribe((res: any) => {
      this.userDTO = res;
    })
  }

  clickedSms: boolean = false;
  clickedEmail: boolean = false;

  clickSms() : void {
    this.clickedSms = !this.clickedSms
    this.clickedEmail = false;
  }

  clickEmail() : void {
    this.clickedEmail = !this.clickedEmail;
    this.clickedSms = false;
  }

  open() {
    if (this.emailForReset == null || this.emailForReset == undefined)
      this.openRegisterVerification();
    else 
      this.openResetPassword();
  }

  openRegisterVerification(){
    if (this.clickedEmail) {
      this.verificationService.sendVerificationMail(this.userDTO.email).subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['verification-code', {type: "sms"}]);
        },
        error: (err: any) => {
          console.log(err);
        }
      })
    } 
    else if (this.clickedSms) {
      this.verificationService.sendVerificationSMS(this.userDTO.email).subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['verification-code', {type: "sms"}]);
        },
        error: (err: any) => {
          console.log(err);
        }
      })
    } else {
      this.snackBar.open("Please select one of the options.", "", {
        duration: 2000,
     });
    }
  }

  openResetPassword(){
    if (this.clickedEmail) {
      this.verificationService.sendResetPasswordEmail(this.emailForReset).subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['reset-password']);
        },
        error: (err: any) => {
          console.log(err);
        }
      })
    } 
    else if (this.clickedSms) {
      this.verificationService.sendResetPasswordSms(this.emailForReset).subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['reset-password']);
        },
        error: (err: any) => {
          console.log(err);
        }
      })
    } else {
      this.snackBar.open("Please select one of the options.", "", {
        duration: 2000,
     });
    }
  }
}
