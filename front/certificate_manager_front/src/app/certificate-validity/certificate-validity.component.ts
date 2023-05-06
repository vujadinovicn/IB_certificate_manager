import { ChangeDetectorRef, Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-certificate-validity',
  templateUrl: './certificate-validity.component.html',
  styleUrls: ['./certificate-validity.component.css']
})
export class CertificateValidityComponent {

  filePath: string = "";
  file: File = {} as File;
  firstOption: boolean = true;
  validity: string = '';

  constructor(private ref: ChangeDetectorRef, private certificateService: CertificateService){

  }

  serialNumberForm = new FormGroup({
    serialNumber: new FormControl('', [Validators.required])
  })

  certNameForm = new FormGroup({
    certName: new FormControl('', [])
  })

  private disableForm() {
    (<any>Object).values(this.certNameForm.controls).forEach((control: FormControl) => {
      control.disable();
    });
  }

  ngOnInit(): void {
    this.disableForm();
  }

  clickedFirst(){
    this.firstOption = true;
    this.ref.detectChanges();
  }

  clickedSecond(){
    this.firstOption = false;
    this.ref.detectChanges();

  }

  onFileSelect(event: any){
    if (event.target.files){
      var reader = new FileReader();
      this.file = event.target.files[0];
      reader.readAsDataURL(this.file);
      reader.onload=(e: any)=>{
        this.filePath = reader.result as string;
      }
      console.log(this.certNameForm.value.certName);
      this.certNameForm.setValue({
        certName: this.file.name,
      })
      // this.certNameForm.value.certName = this.file.name;
    }
  }

  submit(): any{
    this.certificateService.findById(this.filePath).subscribe({
      next: (result) => {
        console.log(result);
      },
      error: (error) => {
        console.log(error.error);
      },
    });
  }

  requestReset(){

  }

}
