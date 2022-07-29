import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { Error404Component } from './pages/error404/error404.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './auth.guard';
import { HomeGuard } from './home.guard';
import { RankingComponent } from './pages/ranking/ranking.component';
import { TrophiesComponent } from './pages/trophies/trophies.component';
import { SettingsComponent } from './pages/settingsPage/settings.component';
import { BookComponent } from './pages/book/book.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AdminGuard } from './admin.guard';

const routes: Routes = [
  {
    path: "login", 
    component: LoginComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "home", 
    component: HomeComponent,
    canActivate: [HomeGuard]
  },
  {
    path: "books/:id", 
    component: BookComponent,
    canActivate: [HomeGuard]
  },
  {
    path: "ranking", 
    component: RankingComponent,
    canActivate: [HomeGuard]
  },
  {
    path: "trophies", 
    component: TrophiesComponent,
    canActivate: [HomeGuard]
  },
  {
    path: "settings",
    component: SettingsComponent,
    canActivate: [HomeGuard]
  },
  {
    path: "admin",
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {
    path: "", 
    redirectTo: "/login", 
    pathMatch: 'full'
  },
  {
    path: "**", 
    component: Error404Component
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
