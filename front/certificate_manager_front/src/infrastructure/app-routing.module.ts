import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from 'src/app/homepage/homepage.component';
import { LoginComponent } from 'src/app/login/login.component';
import { CertificateValidityComponent } from 'src/app/certificate-validity/certificate-validity.component';
import { RequestReviewComponent } from 'src/app/request-review/request-review.component';
import { VerificationCodeComponent } from 'src/app/verification-code/verification-code.component';
import { VerificationChoiceComponent } from 'src/app/verification-choice/verification-choice.component';
import { RegisterComponent } from 'src/app/register/register.component';

const routes: Routes = [
  {path: "validate", component: CertificateValidityComponent},
  {path: "login", component: LoginComponent},
  {path: "all-certificates", component: HomepageComponent},
  {path: "request-review", component: RequestReviewComponent},
  {path: "my-certificates", component: HomepageComponent},
  {path: "register", component: RegisterComponent},

  {path: "verification-code", component: VerificationCodeComponent},
  {path: "verification-choice", component: VerificationChoiceComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
