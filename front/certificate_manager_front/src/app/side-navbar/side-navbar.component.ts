import { UserService } from './../services/user.service';
import { Router } from '@angular/router';
import { AuthService } from './../services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-side-navbar',
  templateUrl: './side-navbar.component.html',
  styleUrls: ['./side-navbar.component.css']
})
export class SideNavbarComponent {

  name = "Tina";

  constructor(private authService: AuthService, private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    let loggedUser = this.authService.getUser();
    this.name = loggedUser? loggedUser.name: "";
    this.handleSmallScreens();
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

}
