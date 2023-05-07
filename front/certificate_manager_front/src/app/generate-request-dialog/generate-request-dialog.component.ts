import { Component, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateService } from '../services/certificate.service';
import { WithdrawDialogComponent } from '../withdraw-dialog/withdraw-dialog.component';

@Component({
  selector: 'app-generate-request-dialog',
  templateUrl: './generate-request-dialog.component.html',
  styleUrls: ['./generate-request-dialog.component.css']
})
export class GenerateRequestDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<GenerateRequestDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private certificateService: CertificateService,
    private snackBar: MatSnackBar
  ) {}

  reason: string = "";
  issuedTo: string = "";
  certType: String[] = ['Root', 'Intermediate', 'End']

  requestForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  ngOnInit(): void {
    this.issuedTo = this.data.serialNumber;
  }

  withdrawCertificate(): void {
    this.certificateService.withdraw(this.issuedTo, this.reason).subscribe({
      next: (res) => {
        this.snackBar.open(res.message, "", {
          duration: 2700, panelClass:['snack-bar-success']
       });
       this.dialogRef.close();
      },
      error: (err) => {
        this.snackBar.open(err.error, "", {
          duration: 2700,
       });
      },
    })
  }

  submit(){

  }

  searchByType(event: any){

  }


}
