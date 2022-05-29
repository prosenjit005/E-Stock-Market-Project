import { Component, OnInit, ViewChild } from '@angular/core';
import { Company, CompanyService } from '../services/company.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorSnackBarComponent } from '../errorSnackBars/error-snack-bar/error-snack-bar.component';
import { SuccessSnackBarComponent } from '../errorSnackBars/success-snack-bar/success-snack-bar.component';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { Stocks, StocksService } from '../services/stocks.service';
import { FormControl, FormGroup } from '@angular/forms';
import {LiveAnnouncer} from '@angular/cdk/a11y';
import {MatSort, Sort} from '@angular/material/sort';

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
  getAllStocksShowFlag = false;

  allCompaniesData: Company[] = [];
  displayedColumns: string[] = ['companyCode', 'companyName', 'companyCEO', 'companyTurnover', 'companyWebsite', 'stockExchange', 'action'];

  companyCode: string = "";
  companyName: string = "";
  companyCEO: string = "";
  companyTurnover!: number;
  companyWebsite: string = "";
  stockExchange: string = "";

  searchcompanyCode: string = "";//search for this company
  searchCompanyData!: Company;
  isSearchBtnClicked: boolean = false;//to know whether the search button was clicked or not

  stocksCompanyCode: string = "";
  stcoksCompanyTurnover!: number;

  stockDateRange: FormGroup;
  stocksSearchCompanyCode: string = "";
  allStocksData: Stocks[] = [];
  displayedColumnsStocks: string[] = ['companyCode', 'stockPrice', 'stockDateTime'];
  isStockSearchClicked: boolean = false;

  constructor(public companyService: CompanyService, private _snackBar: MatSnackBar, public dialog: MatDialog,
    public stocksService: StocksService, private _liveAnnouncer: LiveAnnouncer) {
    const today = new Date();
    const month = today.getMonth();
    const year = today.getFullYear();

    this.stockDateRange = new FormGroup({
      start: new FormControl(new Date(year, month, 10)),
      end: new FormControl(new Date(year, month, 13)),
    });

  }

  ngOnInit(): void {
    this.getAllCompaniesList();
  }

  getAllCompaniesList() {
    this.companyService.getAllCompanies()
      .subscribe(data => {
        this.allCompaniesData = data
      });
  }

  addCompanyAction() {
    this.addCompanyShowFlag = true;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;
    this.getAllStocksShowFlag = false;

  }

  getCompanyAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = true;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;
    this.getAllStocksShowFlag = false;
  }

  searchCompany() {
    if (null != this.searchcompanyCode && this.searchcompanyCode != "") {
      this.isSearchBtnClicked = true;
      this.companyService.getCompanyInfo(this.searchcompanyCode)
        .subscribe(data => {
          console.log(data);
          this.searchCompanyData = data;
        });
    }
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
    this.getAllStocksShowFlag = false;
  }

  addStocksAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = true;
    this.getAllStocksShowFlag = false;
  }

  getAllStocksAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;
    this.getAllStocksShowFlag = true;
  }

  saveCompany() {
    this.companyService.getAllCompanies()
      .subscribe(data => {
        this.allCompaniesData = data

        let companyData = {} as Company;
        companyData.companyCode = this.companyCode;
        companyData.companyName = this.companyName;
        companyData.companyCEO = this.companyCEO;
        companyData.companyTurnover = this.companyTurnover;
        companyData.companyWebsite = this.companyWebsite;
        companyData.stockExchange = this.stockExchange;

        console.log(companyData);

        //this will filter if the companyCode is already present.
        let companyPresentObj = this.allCompaniesData.find(obj => obj.companyCode.toLocaleLowerCase() ==
          companyData.companyCode.toLocaleLowerCase());
        console.log("Company Present=", companyPresentObj);

        if (null != companyPresentObj) {
          this._snackBar.openFromComponent(ErrorSnackBarComponent, {
            duration: 5 * 1000,
            panelClass: ['custom-error-snackbar-style'],
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });


        } else {
          //company code is unique
          this.companyService.saveCompanyDetails(companyData)
            .subscribe(data => {
              console.log(data);
              this._snackBar.openFromComponent(SuccessSnackBarComponent, {
                duration: 5 * 1000,
                panelClass: ['custom-success-snackbar-style'],
                horizontalPosition: 'center',
                verticalPosition: 'top'
              });
            });
        }




      });


  }

  openTableDialog(action: string, element: Company) {
    console.log(element);

    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: element,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("The dialog was closed from home-component");
      this.getAllCompaniesList();
    });
  }

  saveStockPrice() {
    let stockData = {} as Stocks;
    stockData.companyCode = this.stocksCompanyCode;
    stockData.stockPrice = this.stcoksCompanyTurnover;

    console.log(stockData);

    this.stocksService.addNewStock(stockData)
      .subscribe(data => {
        console.log(data);

        if (data == null) {
          this._snackBar.openFromComponent(ErrorSnackBarComponent, {
            duration: 5 * 1000,
            panelClass: ['custom-error-snackbar-style'],
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
        } else {
          this._snackBar.openFromComponent(SuccessSnackBarComponent, {
            duration: 5 * 1000,
            panelClass: ['custom-success-snackbar-style'],
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
        }
      });

  }

  searchCompanyStocks() {
    console.log(this.stocksSearchCompanyCode);
    console.log(this.stockDateRange.value.start);
    console.log(this.stockDateRange.value.end);

    if (null != this.stocksSearchCompanyCode && this.stocksSearchCompanyCode != "") {
      this.stocksService.getStocks(this.stocksSearchCompanyCode, this.stockDateRange.value.start, this.stockDateRange.value.end)
        .subscribe(data => {
          console.log(data);
          this.allStocksData = data;
          this.isStockSearchClicked = true;
        });
    }


  }




}
