import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  @ViewChild('child') childComponent: NavbarComponent | undefined;

  ngAfterViewInit() {
    this.childComponent?.nav?.nativeElement.classList.add('pos-rel');
  }

  resetPasswordForm = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confpass: new FormControl('', [Validators.required])
  }, [])


  code: string = '';

  constructor() { }

  ngOnInit(): void {
    
  }

  resetPassword() {
   
  }

}
