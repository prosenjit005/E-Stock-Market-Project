import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  username: string = "user1";
  password: string = "password1";

  httpBaseCompanyUri: string = "http://52.33.255.113:8989/api/v1.0/market/company";
  httpBaseStocksUri: string = "http://52.33.255.113:8989/api/v1.0/market/stock";


  constructor() { }

}
