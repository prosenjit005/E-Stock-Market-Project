import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from './common.service';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Company {
  id: number;
  companyCode: string;
  companyName: string;
  companyCEO: string;
  companyTurnover: number;
  companyWebsite: string;
  stockExchange: string;
}

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  getAllCompaniesUrl = '/getall';
  saveCompanyUrl = '/register';
  getCompanyInfoUrl = '/info';
  deleteCompanyUrl = '/delete';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('user1:password1')
    })
  };

  constructor(private http: HttpClient, private commonService: CommonService) { }

  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.commonService.httpBaseCompanyUri + this.getAllCompaniesUrl, this.httpOptions)
  }

  saveCompanyDetails(company: Company): Observable<Company> {
    return this.http.post<Company>(this.commonService.httpBaseCompanyUri + this.saveCompanyUrl, company, this.httpOptions);
  }

  getCompanyInfo(companyCode: string): Observable<Company> {
    return this.http.get<Company>(this.commonService.httpBaseCompanyUri + this.getCompanyInfoUrl + "/" + companyCode, this.httpOptions)
  }

  deleteCompany(companyCode: string): Observable<Company> {
    return this.http.delete<Company>(this.commonService.httpBaseCompanyUri + this.deleteCompanyUrl + "/" + companyCode, this.httpOptions)
  }


}
