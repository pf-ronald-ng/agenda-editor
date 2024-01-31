import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { FindAgendasComponent } from './agenda/find-agendas/find.agendas.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'find-agendas', component: FindAgendasComponent },
    { path: '**', redirectTo: '', pathMatch: 'full' } // Redirect to HomeComponent for any unmatched route
];

export { routes };
export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders<RouterModule> = RouterModule.forRoot(routes);
