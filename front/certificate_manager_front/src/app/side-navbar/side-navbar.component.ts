import { CertificateService } from './../services/certificate.service';
import { UserService } from './../services/user.service';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from './../services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-side-navbar',
  templateUrl: './side-navbar.component.html',
  styleUrls: ['./side-navbar.component.css']
})
export class SideNavbarComponent {

  name = "Tina";
  url = '';
  reqByMe: boolean = false;
  reqFromMe: boolean = false;

  constructor(private authService: AuthService, 
              private router: Router, 
              private userService: UserService,
              private certificateService: CertificateService) {
                
                
  }

  ngOnInit(): void {
    let loggedUser = this.authService.getUser();
    this.name = loggedUser? loggedUser.name: "";
    this.handleSmallScreens();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.url = event.urlAfterRedirects;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }


  handleSmallScreens(): void {
    (<HTMLButtonElement>document.querySelector('.navbar-toggler'))
      .addEventListener('click', () => {
      let navbarMenu = <HTMLDivElement>document.querySelector('#side-navbar-container')
      let navbarMenuSmaller = <HTMLDivElement>document.querySelector('#navbar-smaller')
      let closeBtn = <HTMLDivElement>document.querySelector('#close-btn i')
  
      if (navbarMenu.style.display === 'block') {
        navbarMenu.style.display = 'none';
        closeBtn.style.display = 'none';
        navbarMenuSmaller.style.display = 'block';
        return ;
      }
  
      navbarMenu.style.display = 'block';
      closeBtn.style.display = 'block';

      navbarMenuSmaller.style.display = 'none';
    })
  }

  close() {
    let navbarMenu = <HTMLDivElement>document.querySelector('#side-navbar-container');
    let navbarMenuSmaller = <HTMLDivElement>document.querySelector('#navbar-smaller');
    let closeBtn = <HTMLDivElement>document.querySelector('#close-btn i');

    navbarMenu.style.display = 'none';
    closeBtn.style.display = 'none';

    navbarMenuSmaller.style.display = 'block';
  }

  showCertificates(type: string) {
    if (type == 'all') {
      this.certificateService.getAllCertificates().subscribe({
        next: (value) => {
          this.certificateService.setCertificatesToDisplay(value);
        },
        error: (err) => {
          // TODO: make snackbar
          console.log("Error wile trying to fetch all certificates.")
        },
      });
      this.router.navigate(['all-certificates']);
    } else {
      if (type == "my") {
        this.certificateService.getMyCertificates().subscribe({
          next: (value) => {
            this.certificateService.setCertificatesToDisplay(value);
          },
          error: (err) => {
            // TODO: make snackbar
            console.log("Error wile trying to fetch your certificates.")
          },
        });
        this.router.navigate(['my-certificates']);
      }
    }
  }

  openRequestReview(option: boolean) {
    if (option) {
      this.reqByMe = true;
      this.reqFromMe = false;
    }
    else {
      this.reqByMe = false;
      this.reqFromMe = true;
    }
    this.certificateService.setIsByMeSelected(option);
    this.router.navigate(['request-review']);
  }

}
