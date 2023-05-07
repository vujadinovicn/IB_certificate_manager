import { Subscription } from 'rxjs';
import { Cerificate, CertificateService } from './../services/certificate.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {

  certificates: Cerificate[] = [];
  subs: Subscription[] = [];
  loaded = false;
  url = '';

  constructor(private certificateService: CertificateService, private router: Router) {
    
  }

  ngOnInit() {
    // let sub = this.certificateService.getCertificatesToDisplay().subscribe((value) => {
    //   this.certificates = value;
    //   console.log(value);
    // });
    // this.subs.push(sub);
    this.url = this.router.url;
    if (this.url == '/all-certificates') {
      this.certificateService.getAllCertificates().subscribe({
        next: (value) => {
          this.certificates = value;
          this.loaded = true;
        },
        error: (err) => {
          // TODO: make snackbar
          console.log("Error wile trying to fetch all certificates.")
        },
      });
    } else {
      if (this.url == '/my-certificates') {
        this.certificateService.getMyCertificates().subscribe({
          next: (value) => {
            this.certificates = value;
            this.loaded = true;
          },
          error: (err) => {
            // TODO: make snackbar
            console.log("Error wile trying to fetch your certificates.")
          },
        });
      }
    }
  }

  ngOnDestroy() {
    for (let sub of this.subs) {
      sub.unsubscribe();
    }
  }

  formatDate(dataStr: string) {
    return formatDate(dataStr);
  }
}

export function formatDate(dateStr: string) {
  let datePart = dateStr.split('T')[0];
  let tokens = datePart.split('-');
  return tokens[2] + "." + tokens[1] + "." + tokens[0] + ".";
}
