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
import { RegisterComponent } from './register/register.component';
import { MailVerificationComponent } from './mail-verification/mail-verification.component';
<<<<<<< HEAD
import { ResetPasswordComponent } from './reset-password/reset-password.component';
=======
import { VerificationChoiceComponent } from './verification-choice/verification-choice.component';
>>>>>>> 1e12de2b8c1048e78582b214d30fccdbdb102ee8

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    MailVerificationComponent,
<<<<<<< HEAD
    ResetPasswordComponent
=======
    VerificationChoiceComponent
>>>>>>> 1e12de2b8c1048e78582b214d30fccdbdb102ee8
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline', hideRequiredMarker: 'true' }}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
