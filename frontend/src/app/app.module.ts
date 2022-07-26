import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RankingComponent } from './components/ranking/ranking.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// Material UI
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FlexLayoutModule } from '@angular/flex-layout';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './pages/login/login.component';
import { SettingsComponent } from './pages/settingsPage/settings.component';
import { Error404Component } from './components/error404/error404.component';
import { HomeComponent } from './pages/home/home.component';
import { TokenInterceptorService } from './interceptors/TokenInterceptor/token-interceptor.service';
import { AdminComponent } from './pages/admin/admin.component';
import { LoadingComponent } from './components/loading/loading.component';
import { BookCardComponent } from './components/book-card/book-card.component';

@NgModule({
  declarations: [
    AppComponent,
    RankingComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    SettingsComponent,
    Error404Component,
    HomeComponent,
    AdminComponent,
    LoadingComponent,
    BookCardComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatMenuModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatCheckboxModule,
    MatGridListModule,
    MatPaginatorModule,
    MatCardModule,

    
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
