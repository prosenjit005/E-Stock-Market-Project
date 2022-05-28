import { Component, OnInit } from '@angular/core';
import { Company, CompanyService } from '../services/company.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  addCompanyShowFlag = false;
  getCompanyShowFlag = false;
  getAllCompaniesShowFlag = false;
  addStocksShowFlag = false;

  allCompaniesData: Company[] = [];

  companyCode: string = "";
  companyName: string = "";
  companyCEO: string = "";
  companyTurnover!: number;
  companyWebsite: string = "";
  stockExchange: string = "";

  constructor(public companyService: CompanyService) { }

  ngOnInit(): void {
  }

  addCompanyAction() {
    this.addCompanyShowFlag = true;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;

  }

  getCompanyAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = true;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;
  }

  getAllCompaniesAction() {

    this.companyService.getAllCompanies()
      .subscribe(data => {
        this.allCompaniesData = data
        console.log(this.allCompaniesData);
        console.log(this.allCompaniesData.length);
      });

    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = true;
    this.addStocksShowFlag = false;
  }

  addStocksAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = true;
  }

  saveCompany() {
    let companyData = {} as Company;
    companyData.companyCode = this.companyCode;
    companyData.companyName = this.companyName;
    companyData.companyCEO = this.companyCEO;
    companyData.companyTurnover = this.companyTurnover;
    companyData.companyWebsite = this.companyWebsite;
    companyData.stockExchange = this.stockExchange;

    console.log(companyData);

    this.companyService.saveCompanyDetails(companyData)
    .subscribe(data => {
      console.log(data);
    });

  }

}
