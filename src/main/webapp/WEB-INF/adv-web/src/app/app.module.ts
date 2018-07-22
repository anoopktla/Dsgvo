import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LayoutModule } from '@angular/cdk/layout';
import { HttpClientModule } from '@angular/common/http';
import { AdvService } from './app.service';
import {
  MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatCardModule,
  MatMenuModule, MatTabsModule, MatFormFieldModule, MatOptionModule, MatSelectModule, MatInputModule, MatCheckboxModule, MatGridListModule, MatDatepickerModule,
  MatNativeDateModule,MatProgressSpinnerModule,MatSnackBarModule
} from '@angular/material';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateAdvComponent } from './my-nav/my-nav.component';
import { HomePageComponent } from './home-page/home-page.component';
import { AdvComponent } from './adv/adv.component';
import { EmailTemplateComponent } from './third-page/third-page.component';

const appRoutes: Routes = [

  { path: '', component: HomePageComponent },
  {
    path: 'adv', component: AdvComponent,
    children: [
      {
        path: 'create-adv',
        component: CreateAdvComponent,

      },
      {
        path: 'email-template',
        component: EmailTemplateComponent,

      }
    ]
  }
];

@NgModule({
  declarations: [
    AppComponent,
    CreateAdvComponent,
    HomePageComponent,
    AdvComponent,
    EmailTemplateComponent
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
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatSnackBarModule

  ],
  providers: [AdvService],
  bootstrap: [AppComponent]
})
export class AppModule { }
