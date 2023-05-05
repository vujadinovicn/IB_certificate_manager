import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/login/login.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { ResetPasswordComponent } from 'src/app/reset-password/reset-password.component';
import { VerificationChoiceComponent } from 'src/app/verification-choice/verification-choice.component';
import { ForgotPasswordComponent } from 'src/app/forgot-password/forgot-password.component';
import { VerificationCodeComponent } from 'src/app/verification-code/verification-code.component';

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", component: LoginComponent},
  {path: "register", component: RegisterComponent},
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
