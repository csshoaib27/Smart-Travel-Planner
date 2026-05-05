import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  fullName = '';
  phone = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  onSubmit(): void {
    if (!this.username || !this.email || !this.password || !this.fullName) {
      this.toastr.warning('Please fill in all required fields.');
      return;
    }
    this.loading = true;
    this.authService.register(this.username, this.email, this.password, this.fullName, this.phone).subscribe({
      next: () => {
        this.toastr.success('Registration successful! Please log in.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        const msg = err?.error?.message || err?.message || 'Registration failed. Please try again.';
        this.toastr.error(msg);
        this.loading = false;
      }
    });
  }
}
