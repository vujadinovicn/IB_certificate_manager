import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/login/login.component';
import { MailVerificationComponent } from 'src/app/mail-verification/mail-verification.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { SmsCodeComponent } from 'src/app/sms-code/sms-code.component';

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "verification/mail", component: MailVerificationComponent},
  {path: "verification/sms", component: SmsCodeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
