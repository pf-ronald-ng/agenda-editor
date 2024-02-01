import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FindAgendasComponent } from './find-agendas/find-agendas.component';

 export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'find-agendas', component: FindAgendasComponent },
    { path: '**', redirectTo: '', pathMatch: 'full' } // Redirect to HomeComponent for any unmatched route
  ];
