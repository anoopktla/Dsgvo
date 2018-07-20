import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AdvService {
  constructor(private http: HttpClient) { }
  // baseUrl: string = 'https://dsgvo.herokuapp.com/adv-service/v1/';
  baseUrl: string = 'http://192.168.0.26:8080/adv-service/v1/';

  createAdv(adv) {
    return this.http.post(this.baseUrl+'customers', adv);
  }
}