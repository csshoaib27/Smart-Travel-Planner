import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  topDestinations: any[] = [];
  loading = true;

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.api.getTopRatedDestinations().subscribe({
      next: (res) => {
        this.topDestinations = res.data?.slice(0, 6) ?? [];
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  search(query: string): void {
    this.router.navigate(['/destinations'], { queryParams: { country: query } });
  }
}
