import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../services/company.service';

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

  constructor(public companyService:CompanyService) { }

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

}
