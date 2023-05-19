import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from 'src/app/homepage/homepage.component';
import { LoginComponent } from 'src/app/login/login.component';
import { CertificateValidityComponent } from 'src/app/certificate-validity/certificate-validity.component';
import { RequestReviewComponent } from 'src/app/request-review/request-review.component';
import { PasswordRotationComponent } from 'src/app/password-rotation/password-rotation.component';
import { ForgotPasswordComponent } from 'src/app/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from 'src/app/reset-password/reset-password.component';
import { VerificationChoiceComponent } from 'src/app/verification-choice/verification-choice.component';
import { VerificationCodeComponent } from 'src/app/verification-code/verification-code.component';

const routes: Routes = [
  {path: "validate", component: CertificateValidityComponent},
  {path: "login", component: LoginComponent},
  {path: "all-certificates", component: HomepageComponent},
  {path: "request-review", component: RequestReviewComponent},
  {path: "my-certificates", component: HomepageComponent},
  {path: "password-rotation", component: PasswordRotationComponent},
  {path: "forgot-password", component: ForgotPasswordComponent},
  {path: "reset-password", component: ResetPasswordComponent},
  {path: "verification", component: VerificationChoiceComponent},
  {path: "verification-code", component: VerificationCodeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
