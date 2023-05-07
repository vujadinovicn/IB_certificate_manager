import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from 'src/app/homepage/homepage.component';
import { LoginComponent } from 'src/app/login/login.component';
import { CertificateValidityComponent } from 'src/app/certificate-validity/certificate-validity.component';

const routes: Routes = [
  {path: "certificte-validity", component: CertificateValidityComponent},
  {path: "login", component: LoginComponent},
  {path: "all-certificates", component: HomepageComponent},
  {path: "my-certificates", component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
