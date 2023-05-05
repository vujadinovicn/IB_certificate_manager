import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/login/login.component';
import { MailVerificationComponent } from 'src/app/mail-verification/mail-verification.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { ResetPasswordComponent } from 'src/app/reset-password/reset-password.component';
import { VerificationChoiceComponent } from 'src/app/verification-choice/verification-choice.component';
import { SmsCodeComponent } from 'src/app/sms-code/sms-code.component';

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "verification/mail", component: MailVerificationComponent},
  {path: "reset-password", component: ResetPasswordComponent},
  {path: "verification", component: VerificationChoiceComponent},
  {path: "verification/sms", component: SmsCodeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
