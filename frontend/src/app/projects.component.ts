import { Component, ApplicationRef, Input , Output , EventEmitter } from '@angular/core';
import { FormGroup , FormBuilder , Validators} from '@angular/forms'
import { TammsService } from './tamms.service'
import { Project } from './project'
import { Developer } from './developer'
import { MdSnackBar} from '@angular/material'


@Component({
  selector: 'manage-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent {
  newProject: Project;
  newDeveloper: Developer;
  projects: Project[];
  developers: Developer[];
  newDeveloperForm: FormGroup;
  newProjectForm: FormGroup;
  @Output() projectsChange  = new EventEmitter<Object>();


  constructor(private tammsService: TammsService , 
            private applicationRef: ApplicationRef,
            public formBuilder: FormBuilder,
            private snackBar : MdSnackBar) { 
      this.newProject = new Project()
      this.newDeveloper = new Developer()
  }

  ngOnInit(): void {
    this.getProjects();
    this.getDevelopers();

    this.newDeveloperForm = this.formBuilder.group({
      newDeveloperName : ['',Validators.required]
    });

    this.newProjectForm = this.formBuilder.group({
      newProjectName : ['' , Validators.required ]
    });
  }

  getProjects(): void {
    this.tammsService
        .getProjects()
        .then((projects) =>{
          projects => 
          this.projects = projects; 
          this.projectsChange.emit(this.projects);
        } );
  }

  getDevelopers() : void{
    this.tammsService
        .getDevelopers().
        then((developers) => 
        {
          this.developers = developers; 
          this.projectsChange.emit(this.developers);
      })
  }

  saveNewProject(): void {
   this.tammsService
      .saveNewProject(this.newProject)
      .then(
        (project) =>{
          this.getProjects();
          this.newProject = new Project;
          this.newProjectForm.reset();
          this.snackBar.open('Project ' +  project.name + ' Added ' , '', {
                 duration: 3000, 
                 extraClasses:['confirmToast']
          })
        });
  }

  saveNewDeveloper(): void {
   this.tammsService.saveNewDeveloper(this.newDeveloper)
      .then(developer => {
        this.getDevelopers();
        this.newDeveloper = new Developer;
        this.newDeveloperForm.reset();
        this.newDeveloperForm.markAsPristine();
        this.newDeveloperForm.markAsUntouched();
        this.snackBar.open('Developer ' +  developer.name + ' Added ' , '', {
                 duration: 3000,
                 extraClasses:['.confirmToast']
          })
      });
  }
}
