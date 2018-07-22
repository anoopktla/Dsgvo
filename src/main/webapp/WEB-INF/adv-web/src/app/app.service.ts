import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AdvService {
  constructor(private http: HttpClient) { }
  baseUrl: string = 'https://dsgvo.herokuapp.com/adv-service/v1/';
  createAdv(adv) {
    return this.http.post(this.baseUrl+'customers', adv);
  }
  getCountries() {
    return this.http.get(this.baseUrl+'countries');
  }
}
