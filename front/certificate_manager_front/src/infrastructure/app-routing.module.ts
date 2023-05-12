import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from 'src/app/homepage/homepage.component';
import { LoginComponent } from 'src/app/login/login.component';
import { CertificateValidityComponent } from 'src/app/certificate-validity/certificate-validity.component';
import { RequestReviewComponent } from 'src/app/request-review/request-review.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { VerificationChoiceComponent } from 'src/app/verification-choice/verification-choice.component';
import { VerificationCodeComponent } from 'src/app/verification-code/verification-code.component';

const routes: Routes = [
  {path: "validate", component: CertificateValidityComponent},
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "verification-choice", component: VerificationChoiceComponent},
  {path: "verification-code", component: VerificationCodeComponent},
  {path: "all-certificates", component: HomepageComponent},
  {path: "request-review", component: RequestReviewComponent},
  {path: "my-certificates", component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
