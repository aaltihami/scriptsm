import { Component , OnInit} from '@angular/core';
import { Project } from './project'
import { Developer } from './developer'
import { Sprint } from './sprint'
import { TammsService } from './tamms.service'

import { MdDialogRef  , MdDialog} from '@angular/material'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: []
})
export class AppComponent {
  project : Project;
  developer : Developer;
  sprint : Sprint;  
  dialogRef: MdDialogRef<any>;
  
  constructor(private tammsService : TammsService , private dialog: MdDialog){
    this.project  = null;
    this.developer = null;
    this.sprint = null;
  }

 ngOnInit(): void {
    this.tammsService.getWorkingDeveloperEmitter()
      .subscribe(developer => this.developer = developer);

    this.tammsService.getWorkingSprintEmitter()
      .subscribe(sprint => this.sprint = sprint);

    this.tammsService.getWorkingProjectEmitter()
      .subscribe(project => this.project = project);
  }


  showAboutDialog(): void{
    this.dialogRef = this.dialog.open(DialogComponent);
    this.dialogRef.afterClosed().subscribe(result => {
      this.dialogRef = null;
    });
  }

  manageDB() : void{
      window.open("/h2-console" , "_blank");
  }

  downloadSprintScripts():void{
    window.open("/tamms/sprintScriptsFiles/" + this.sprint.id, "_blank");
  }
}


@Component({
  selector: 'about-dialog',
  template: `
  <h2>About script manager</h2>
  <div>
    Scripts manager generates the rollback scripts for the following sql server statements:
    <ul>
      <li>Create Table</li>
      <li>Create Procedure</li>
      <li>Add Column</li>
      <li>Insert</li>
      <li>Alter Procedure</li>
    </ul> 
  </div>
  
  <div fxLayout="column" fxLayoutAlign="center center"  >
  <button  md-raised-button (click)="dialogRef.close()">Close</button>
  </div>`
})
export class DialogComponent {
  constructor(public dialogRef: MdDialogRef<any>) { 
  }
}