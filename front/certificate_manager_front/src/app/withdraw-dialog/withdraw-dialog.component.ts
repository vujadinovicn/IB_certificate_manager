import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../services/auth.service';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-withdraw-dialog',
  templateUrl: './withdraw-dialog.component.html',
  styleUrls: ['./withdraw-dialog.component.css']
})
export class WithdrawDialogComponent implements OnInit{

  constructor(
    public dialogRef: MatDialogRef<WithdrawDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private certificateService: CertificateService,
    private snackBar: MatSnackBar
  ) {}

  reason: string = "";
  serialNumber: string = "";

  ngOnInit(): void {
    this.serialNumber = this.data.serialNumber;
  }

  withdrawCertificate(): void {
    this.certificateService.withdraw(this.serialNumber, this.reason).subscribe({
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


}
