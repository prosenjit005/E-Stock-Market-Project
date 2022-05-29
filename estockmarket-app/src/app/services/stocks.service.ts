import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';

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
  searchStockUrl = '/get';
  datePipe = new DatePipe('en-US');

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

  getStocks(companyCode: string, startDate: Date, endDate: Date): Observable<Stocks[]> {
    let startDateMod = this.datePipe.transform(startDate, 'dd-MM-yyyy');
    let endDateMod = this.datePipe.transform(endDate, 'dd-MM-yyyy');

    return this.http.get<Stocks[]>(this.commonService.httpBaseStocksUri + this.searchStockUrl + "/" +
      companyCode + "/" + startDateMod + "/" + endDateMod, this.httpOptions)
  }

}
