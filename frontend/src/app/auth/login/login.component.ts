import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  // Login
  username = '';
  password = '';
  loading = false;

  // Reset password
  mode: 'login' | 'reset' = 'login';
  resetUsername = '';
  resetEmail = '';
  resetNewPassword = '';
  resetConfirmPassword = '';
  resetLoading = false;

  constructor(
    private authService: AuthService,
    private api: ApiService,
    private router: Router,
    private toastr: ToastrService
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
    }
  }

  onSubmit(): void {
    if (!this.username || !this.password) {
      this.toastr.warning('Please enter your username or email and password.');
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

  switchToReset(): void {
    this.mode = 'reset';
    this.resetUsername = '';
    this.resetEmail = '';
    this.resetNewPassword = '';
    this.resetConfirmPassword = '';
  }

  switchToLogin(): void {
    this.mode = 'login';
  }

  onResetSubmit(): void {
    if (!this.resetUsername || !this.resetEmail || !this.resetNewPassword) {
      this.toastr.warning('Please fill in all fields.');
      return;
    }
    if (this.resetNewPassword.length < 6) {
      this.toastr.warning('Password must be at least 6 characters.');
      return;
    }
    if (this.resetNewPassword !== this.resetConfirmPassword) {
      this.toastr.warning('Passwords do not match.');
      return;
    }
    this.resetLoading = true;
    this.api.resetPassword(this.resetUsername, this.resetEmail, this.resetNewPassword).subscribe({
      next: (res) => {
        this.toastr.success('Password reset! You can now log in.');
        this.resetLoading = false;
        this.mode = 'login';
        this.username = this.resetUsername;
      },
      error: (err) => {
        this.toastr.error(err?.error?.message || 'No account found with that username and email.');
        this.resetLoading = false;
      }
    });
  }
}
