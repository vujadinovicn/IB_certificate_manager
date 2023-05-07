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
  selectedType: string = '';

  requestForm = new FormGroup({
    date: new FormControl('', [Validators.required])
  });

  ngOnInit(): void {
    this.issuedTo = this.data.issuerNumber;
  }

  generateRequest(): void {
    if (!this.requestForm.valid || this.selectedType == ''){
      this.snackBar.open("Check inputs again!", "", {
        duration: 2700, panelClass: ['snack-bar-success']
     });
     return;
    }

    this.certificateService.generateRequest(
      {
        validTo: new Date(this.requestForm.value.date!).toISOString(),
        issuerSerialNumber: this.issuedTo,
        type: this.selectedType.toUpperCase()
      }
    ).subscribe({
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

  selectType(event: any){
    this.selectedType = event.value;
  }


}
