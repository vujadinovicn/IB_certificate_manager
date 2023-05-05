import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-verification-choice',
  templateUrl: './verification-choice.component.html',
  styleUrls: ['./verification-choice.component.css']
})
export class VerificationChoiceComponent implements OnInit{

  constructor(private router: Router, public snackBar: MatSnackBar){
    
  }
  
  ngOnInit(): void {

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
    if (this.clickedEmail) {
      this.router.navigate(['verification/mail'])
      // pozovi endpoint send/verification/email/{email}
    } 
    else if (this.clickedSms) {
      console.log('biceee')
        // pozovi endpoint send/verification/sms/{email}

    } else {
      this.snackBar.open("Please select one of the options.", "", {
        duration: 2000,
     });
    }
  }
}
