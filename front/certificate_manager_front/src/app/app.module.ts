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
import { SideNavbarComponent } from './side-navbar/side-navbar.component';
import { HomepageComponent } from './homepage/homepage.component';
import { CertificateValidityComponent } from './certificate-validity/certificate-validity.component';
import { TokenInterceptor } from './interceptor/token-interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestReviewComponent } from './request-review/request-review.component';
import { WithdrawDialogComponent } from './withdraw-dialog/withdraw-dialog.component';
import { GenerateRequestDialogComponent } from './generate-request-dialog/generate-request-dialog.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { VerificationChoiceComponent } from './verification-choice/verification-choice.component';
import { VerificationCodeComponent } from './verification-code/verification-code.component';
import { NgxOtpInputModule } from 'ngx-otp-input';
import { PasswordRotationComponent } from './password-rotation/password-rotation.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    SideNavbarComponent,
    HomepageComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    VerificationChoiceComponent,
    VerificationCodeComponent,
    CertificateValidityComponent,
    RequestReviewComponent,
    WithdrawDialogComponent,
    GenerateRequestDialogComponent,
    PasswordRotationComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxOtpInputModule
  ],
  providers: [
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline', hideRequiredMarker: 'true' }},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
