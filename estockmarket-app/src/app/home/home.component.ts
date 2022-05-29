import { Component, OnInit } from '@angular/core';
import { Company, CompanyService } from '../services/company.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorSnackBarComponent } from '../errorSnackBars/error-snack-bar/error-snack-bar.component';
import { SuccessSnackBarComponent } from '../errorSnackBars/success-snack-bar/success-snack-bar.component';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';

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

  constructor(public companyService: CompanyService, private _snackBar: MatSnackBar, public dialog: MatDialog) { }

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

  }

  getCompanyAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = true;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = false;
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
  }

  addStocksAction() {
    this.addCompanyShowFlag = false;
    this.getCompanyShowFlag = false;
    this.getAllCompaniesShowFlag = false;
    this.addStocksShowFlag = true;
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

}
