import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from '../infrastructure/app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/infrastructure/material.module';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { NgxOtpInputModule } from 'ngx-otp-input';
import { SmsCodeComponent } from './sms-code/sms-code.component';
// import { OtpInputModule } from 'ngx-otp-input';
import { RegisterComponent } from './register/register.component';
import { MailVerificationComponent } from './mail-verification/mail-verification.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { VerificationChoiceComponent } from './verification-choice/verification-choice.component';
import { NgxOtpInputModule } from 'ngx-otp-input';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent, 
    SmsCodeComponent,
    RegisterComponent,
    MailVerificationComponent,
    ResetPasswordComponent,
    VerificationChoiceComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxOtpInputModule
  ],
  providers: [
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline', hideRequiredMarker: 'true' }}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
