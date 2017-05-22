import { Component , OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { Project } from './project'
import { Developer } from './developer'
import { TammsService } from './tamms.service'
import { FormBuilder , FormControl, FormGroup, Validators} from '@angular/forms'
@Component({
  selector: 'tamms-start',
  templateUrl: './tamms-start.component.html',
  styleUrls: ['./tamms-start.component.css']
})
export class TammsStartComponent {
  started: boolean = false
  projects: Project[];
  developers: Developer[];
  selectedProjectId: string;
  selectedDeveloperId: string;
  startForm: FormGroup;

  constructor(private tammsService: TammsService,
              private router: Router,
              public formBuilder: FormBuilder) { 
  }

  getProjects(): void {
    this.tammsService
        .getProjects()
        .then(projects => this.projects = projects);
  }

  projectsChange(): void{
    this.getProjects();
    this.getDevelopers();
  }

  getDevelopers(): void {
    this.tammsService
        .getDevelopers()
        .then(developers => this.developers = developers);
  }

  ngOnInit(): void {
    this.getProjects();
    this.getDevelopers();

   this.startForm =  this.formBuilder.group({
          selectedProjectId: ['' , Validators.required],
          selectedDeveloperId: ['' , Validators.required]  
        })
  }

  start():void{
    this.router.navigate(['/sprints', {pId:this.selectedProjectId, dId:this.selectedDeveloperId}]);
  }

  onStartFormSubmit():void{
    if(this.startForm.invalid){
        return;
    }
    this.start();
  }
}
