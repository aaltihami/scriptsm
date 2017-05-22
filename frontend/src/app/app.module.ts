import { BrowserModule } from '@angular/platform-browser';
import { NgModule , Input } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule , MdDialogModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout'
import { TammsStartComponent } from './tamms-start.component'
import { TammsService } from './tamms.service'
import { SprintsComponent } from './sprints.component'
import 'hammerjs';

import { AppComponent, DialogComponent} from './app.component';
import { ProjectsComponent } from './projects.component'
import { TammsRoutingModule } from './tamms-routing.module'
import { StringReplacePipe } from './string-replace.pipe'

@NgModule({
  declarations: [
    AppComponent,
    TammsStartComponent,
    ProjectsComponent,
    SprintsComponent,
    StringReplacePipe,
    DialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    TammsRoutingModule,
    MdDialogModule
  ],
  entryComponents :[DialogComponent]  ,
  providers: [TammsService],
  bootstrap: [AppComponent]
})
export class AppModule {}
