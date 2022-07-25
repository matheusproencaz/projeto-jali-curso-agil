import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { Error404Component } from './components/error404/error404.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './auth.guard';
import { HomeGuard } from './home.guard';
import { RankingComponent } from './components/ranking/ranking.component';

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
    path: "ranking", 
    component: RankingComponent,
    canActivate: [HomeGuard]
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
