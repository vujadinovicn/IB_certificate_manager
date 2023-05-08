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
    console.log(this.emailForReset)
    if (this.emailForReset != null && this.emailForReset != undefined)
      this.openRegisterVerification();
    else 
      this.openResetPassword();
  }

  openRegisterVerification(){
    if (this.clickedEmail) {
      this.verificationService.sendVerificationMail(this.userDTO.email).subscribe({
        next: (res: any) => {
          this.snackBar.open("Email with verification code has been sent", "", {
            duration: 2700, panelClass: ['snack-bar-success']
        });
          this.router.navigate(['verification-code', {type: "sms"}]);
        },
        error: (err: any) => {
          this.snackBar.open(err.error, "", {
            duration: 2700, panelClass: ['snack-bar-server-error']
         });
        }
      })
    } 
    else if (this.clickedSms) {
      this.verificationService.sendVerificationSMS(this.userDTO.email).subscribe({
        next: (res: any) => {
          this.snackBar.open("SMS with verification code has been sent", "", {
            duration: 2700, panelClass: ['snack-bar-success']
        });
          this.router.navigate(['verification-code', {type: "sms"}]);
        },
        error: (err: any) => {
          this.snackBar.open(err.error, "", {
            duration: 2700, panelClass: ['snack-bar-server-error']
         });
        }
      })
    } else {
      this.snackBar.open("Please select one of the options.", "", {
        duration: 2700, panelClass: ['snack-bar-front-error'] 
     });
    }
  }

  openResetPassword(){
    console.log("eee")
    if (this.clickedEmail){
      this.router.navigate(['forgot-password', {email: this.clickedEmail}])
    } else if (this.clickedSms)  {
      this.router.navigate(['forgot-password', {email: this.clickedEmail}])
    } else{
      this.snackBar.open("Please select one of the options.", "", {
        duration: 2700,  panelClass: ['snack-bar-front-error'] 
     });
    
    }
  }
}
