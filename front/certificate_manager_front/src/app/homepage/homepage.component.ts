import { Subscription } from 'rxjs';
import { Cerificate, CertificateService } from './../services/certificate.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { WithdrawDialogComponent } from '../withdraw-dialog/withdraw-dialog.component';
import * as saveAs from 'file-saver';

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
  role: string = '';

  constructor(private dialog: MatDialog, private authService: AuthService, private certificateService: CertificateService, private router: Router) {
    
  }

  ngOnInit() {
    // let sub = this.certificateService.getCertificatesToDisplay().subscribe((value) => {
    //   this.certificates = value;
    //   console.log(value);
    // });
    // this.subs.push(sub);

    this.role = this.authService.getRole();
    this.url = this.router.url;
    console.log(this.role)
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

  withdraw(certificate: any){
    this.dialog.open(WithdrawDialogComponent, {
      data: {serialNumber: certificate.serialNumber}
    });
  }

  download(serialNumber: string) {
    this.certificateService.download(serialNumber).subscribe({
      next: (value) => {
        saveAs(value, serialNumber);
      }, 
      error: (err) => {
        console.log(err);
      },
    })
  }
}

export function formatDate(dateStr: string) {
  let datePart = dateStr.split('T')[0];
  let tokens = datePart.split('-');
  return tokens[2] + "." + tokens[1] + "." + tokens[0] + ".";
}
