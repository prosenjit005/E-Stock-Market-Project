import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Company, CompanyService } from '../services/company.service';

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.css']
})
export class DeleteDialogComponent implements OnInit {

  isDeleteSuccess: boolean = false;
  allCompaniesData: Company[] = [];

  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Company,
    public companyService: CompanyService) { }

  ngOnInit(): void {
    this.dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onDeleteClick(): void {
    console.log(this.data);

    this.companyService.deleteCompany(this.data.companyCode)
      .subscribe(data => {
        console.log(data);
        this.isDeleteSuccess = true;
      });
  }

  getAllCompaniesList() {
    this.companyService.getAllCompanies()
      .subscribe(data => {
        this.allCompaniesData = data
      });
  }

}
