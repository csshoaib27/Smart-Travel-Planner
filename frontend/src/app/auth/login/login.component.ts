import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
    }
  }

  onSubmit(): void {
    if (!this.username || !this.password) {
      this.toastr.warning('Please enter username and password.');
      return;
    }
    this.loading = true;
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        this.toastr.success('Login successful!');
        this.router.navigate(['/']);
      },
      error: () => {
        this.toastr.error('Invalid username or password.');
        this.loading = false;
      }
    });
  }
}
