import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LayoutModule } from '@angular/cdk/layout';
import { HttpClientModule } from '@angular/common/http';
import { AdvService} from './app.service';
import {
  MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatCardModule,
  MatMenuModule,  MatTabsModule, MatFormFieldModule,MatOptionModule ,MatSelectModule,MatInputModule,MatCheckboxModule,MatGridListModule,MatDatepickerModule,
  MatNativeDateModule
} from '@angular/material';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MyNavComponent } from './my-nav/my-nav.component';
import { HomePageComponent } from './home-page/home-page.component';
import { SecondPageComponent } from './second-page/second-page.component';
import { ThirdPageComponent } from './third-page/third-page.component';

const appRoutes: Routes = [
  { path: 'create-adv', component: MyNavComponent },
  { path: '', component: HomePageComponent },
  { path: 'second-page', component: SecondPageComponent },
  { path: 'third-page', component: ThirdPageComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    MyNavComponent,
    HomePageComponent,
    SecondPageComponent,
    ThirdPageComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatMenuModule,
    MatTabsModule,
    MatOptionModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule
    
  
  ],
  providers: [AdvService],
  bootstrap: [AppComponent]
})
export class AppModule { }
