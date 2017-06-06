import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SprintsComponent } from './sprints.component';
import { TammsStartComponent } from './tamms-start.component'
const routes: Routes = [
  { path: '', redirectTo: '/start', pathMatch: 'full' },
  { path: 'start', component: TammsStartComponent},
  { path: 'sprints',  component: SprintsComponent }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class TammsRoutingModule {}
