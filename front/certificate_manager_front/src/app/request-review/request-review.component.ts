import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Cerificate, CertificateRequest, CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-request-review',
  templateUrl: './request-review.component.html',
  styleUrls: ['./request-review.component.css']
})
export class RequestReviewComponent {

  _role: String = 'ROLE_ADMIN';
  currentOption: string = "pending";
  areByMe: boolean = true;
  requests: CertificateRequest[] = []
  selectedRequestId: number = 2;
  pendingRequests: CertificateRequest[] = []
  notPendingRequests: CertificateRequest[] = []
  

  constructor(private authService: AuthService, private certificateService: CertificateService) {
      this._role = this.authService.getRole();
  }

  ngOnInit(): void {
    this.areByMe = this.certificateService.getIsByMeSelected();
    if(this.areByMe) {
      this.getRequestsByMe()
    }
    else {
      this.getRequestsFromMe()
    }
  }


  getRequestsByMe() {
    this.certificateService.getAllRequestesByMe().subscribe({
      next: (res) => {
        this.separateRequests(res);
      },
      error: (error: any) => {
        this.requests = [];
      }
    });
  }

  getRequestsFromMe() {
    this.certificateService.getAllRequestesFromMe().subscribe({
      next: (res) => {
        this.separateRequests(res);
      },
      error: (error: any) => {
        this.requests = [];
      }
    });
  }

  separateRequests(res :CertificateRequest[]) {
    this.pendingRequests = [];
    this.notPendingRequests = [];
    for (let req of res) {
      if (req.status == 'PENDING') {
        this.pendingRequests.push(req);
      }
      else {
        this.notPendingRequests.push(req);
      }
    }

    if (this.currentOption == 'PENDING') {
      this.requests = this.pendingRequests;
    }
    else {
      this.requests = this.notPendingRequests;
    }
  }


  changeTab(option : string) {
    this.currentOption = option;
    if (this.currentOption == 'pending') {
      this.requests = this.pendingRequests;
    }
    else {
      this.requests = this.notPendingRequests;
    }
  }
}
