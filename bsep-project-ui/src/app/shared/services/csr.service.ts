import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';
import { Csr } from '../../user/models/Csr';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CsrService {
  url: string = `${environment.baseUrl}/${ApiPaths.Csr}`;

  constructor(private http: HttpClient) {}

  postCsr(csr: Csr): Observable<void> {
    let url = `${this.url}/sendCsr`;
    return this.http.post<void>(url, csr);
  }

  getPendingCsrs(): Observable<Csr[]> {
    let url = `${this.url}/all/PENDING`;
    return this.http.get<Csr[]>(url);
  }

  rejectCsr(id: number): Observable<void> {
    let url = `${this.url}/reject/${id}`;
    return this.http.get<void>(url);
  }
}
