import { Component, ApplicationRef, Input } from '@angular/core'
import { Location } from '@angular/common'
import { TammsService } from './tamms.service'
import { ActivatedRoute, Params, Router } from '@angular/router'
import { Project } from './project'
import { Developer } from './developer'
import { Sprint } from './sprint'
import { ScriptSnippet } from './script-snippet'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms'
import { StringReplacePipe } from './string-replace.pipe'

@Component({
  selector: 'manage-sprints',
  templateUrl: './sprints.component.html',
  styleUrls: ['./sprints.component.css']
})
export class SprintsComponent {
  project: Project;
  projectURL: string = "/tamms/projects/"
  developer: Developer;
  sprints: Sprint[];
  newSprint: Sprint;
  newScriptSnippet: ScriptSnippet;
  selectedSprint: Sprint;
  scriptSnippets: ScriptSnippet[];
  newSprintForm: FormGroup;
  newSnippetForm: FormGroup;


  constructor(private tammsService: TammsService,
    private applicationRef: ApplicationRef,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.project = new Project()
    this.developer = new Developer()
    this.newSprint = new Sprint()
    this.newScriptSnippet = new ScriptSnippet()
  }

  ngOnInit(): void {
    this.loadProject();
    this.loadSprints();
    this.loadDeveloper();

    this.newSprintForm = this.formBuilder.group({
      newSprintName: ['', Validators.compose([Validators.required, Validators.maxLength(16)])]
    });

    this.newSnippetForm = this.formBuilder.group({
      newSnippetSQL: ['', Validators.compose([Validators.required ])]
    } , {validator : customValidator }  );
  }


  loadProject(): void {
    this.route.params
      .switchMap((params: Params) => this.tammsService.getProject(params['pId']))
      .subscribe((project) => {

        if (project == null) {
          this.router.navigate(['/start']);
          return;
        }
        this.project = project;
        this.newSprint.project = (this.projectURL + this.project.id);
        this.tammsService.emitWorkingProject(project);
      }
      );
  }

  loadSprints(): void {
    this.route.params
      .switchMap((params: Params) => this.tammsService.getSprints(params['pId']))
      .subscribe(
      sprints => {
        if (sprints == null) {
          this.router.navigate(['/start']);
          return;
        }
        this.sprints = sprints;

        this.onSprintClicked(sprints[0]);

      });
  }

  loadDeveloper(): void {
    this.route.params
      .switchMap((params: Params) => this.tammsService.getDeveloper(params['dId']))
      .subscribe((developer) => {

        if (developer == null) {
          this.router.navigate(['/start']);
          return;
        }
        this.developer = developer;
        this.tammsService.emitWorkingDeveloper(developer);
      }
      );
  }

  getScriptSnippets(sId: string): void {
    this.tammsService.getSprintScriptsSnippets(sId)
      .then(scriptSnippets => this.scriptSnippets = scriptSnippets);
  }

  saveNewSprint(): void {
    this.tammsService
      .saveNewSprint(this.newSprint)
      .then(sprint => {
        this.loadSprints(); this.newSprint.name = '';
      });
  }

  saveNewScriptSnippet(): void {
    this.tammsService.saveNewScriptSnippet(this.newScriptSnippet)
      .then(scriptSnippet => {
        this.getScriptSnippets("" + this.selectedSprint.id);
      });
  }

  onSprintClicked(sprint: Sprint) {
    this.selectedSprint = sprint;
    this.newScriptSnippet.sprint = "/tamms/sprints/" + sprint.id;
    this.getScriptSnippets("" + sprint.id);
    this.tammsService.emitWorkingSprint(sprint);
  }

  downloadSprintScripts():void{
    window.open("/tamms/sprintScriptsFiles/" + this.selectedSprint.id);
  }

}

function customValidator(formGroup: FormGroup){

  if(formGroup.get('newSnippetSQL') == null || formGroup.get('newSnippetSQL') == undefined){
    return null;
  }

  let sql: string = formGroup.get('newSnippetSQL').value;

  if(sql == null || sql == undefined){
    return null;
  }

  console.log('sql :' + sql);

  let cacDEVPosition : number = sql.toUpperCase().search('CAC_DEV');
  let cacQAPosition: number = sql.toUpperCase().search('CAC_QA') ;
  let alterPosition : number = sql.toUpperCase().search('ALTER\\sPROCEDURE');
  let rollbackPosition: number = sql.search('#procedure-rollbak#') ;
  
   if (alterPosition >= 0 && (rollbackPosition < 0 || rollbackPosition > alterPosition)){
       return {'noRollback' : true};
   }
 
  if(cacDEVPosition >= 0 || cacQAPosition >= 0){
    return {'invalidTokens' : true};
  }
  
   return null;
}