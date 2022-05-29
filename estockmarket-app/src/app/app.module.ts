import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { FormsModule } from '@angular/forms';
import { CommonService } from './services/common.service';
import { CompanyService } from './services/company.service';
import { StocksService } from './services/stocks.service';
import { HttpClientModule } from '@angular/common/http';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    DeleteDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule, HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [CommonService, CompanyService, StocksService],
  bootstrap: [AppComponent]
})
export class AppModule { }
