import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';
import { Csr } from '../../user/models/Csr';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CertificateService {
  url: string = `${environment.baseUrl}/${ApiPaths.Certificate}`;

  constructor(private http: HttpClient) {}

  getAllCertificates(): Observable<Csr[]> {
    let url = `${this.url}/all`;
    return this.http.get<Csr[]>(url);
  }

  validateCertificate(alias: string): Observable<boolean> {
    let url = `${this.url}/validate/${alias}`;
    return this.http.get<boolean>(url);
  }

  cancelCertificate(alias: string): Observable<Csr[]> {
    let url = `${this.url}/cancel/${alias}`;
    return this.http.get<Csr[]>(url);
  }
}
