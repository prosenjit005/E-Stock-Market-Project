import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Observable } from 'rxjs';

export interface Stocks {
  id: number;
  companyCode: string;
  stockPrice: number;
  stockDateTime: Date;
}

@Injectable({
  providedIn: 'root'
})
export class StocksService {

  addNewStockUrl = '/add';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('user1:password1')
    })
  };

  constructor(private http: HttpClient, private commonService: CommonService) { }

  addNewStock(stock: Stocks): Observable<Stocks> {
    return this.http.post<Stocks>(this.commonService.httpBaseStocksUri + this.addNewStockUrl + "/" + stock.companyCode,
      stock, this.httpOptions);
  }

}
