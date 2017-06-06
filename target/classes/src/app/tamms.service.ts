import { Injectable, EventEmitter } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Project } from './project';
import { Developer } from './developer';
import { Sprint } from './sprint';
import { ScriptSnippet } from './script-snippet'

@Injectable()
export class TammsService {
  private headers = new Headers({ 'Content-Type': 'application/json' });
  private projectsUrl = '/tamms/projects';
  private developersUrl = '/tamms/developers';
  private sprintsUrl = '/tamms/sprints';
  private scriptSnippetUrl = '/tamms/scriptSnippets';
  private findSprintsByProjectIdUrl = '/tamms/sprints/search/findByProjectId?projectId=';
  private workingProjectEmitter = new EventEmitter<Project>();
  private workingSprintEmitter = new EventEmitter<Sprint>();
  private workingDeveloperEmitter = new EventEmitter<Developer>();


  constructor(private http: Http) { 
    
  }

  getProjects(): Promise<Project[]> {
    return this.http.get(this.projectsUrl)
      .toPromise()
      .then(response => response.json()._embedded.projects as Project[])
      .catch(this.handleError);
  }

  getProject(pId: string): Promise<Project> {
    let projectId = +pId;
    console.log('projectId' + isNaN(projectId));
    if (isNaN(projectId)) {
      return Promise.resolve(null);
    }

    return this.http.get(this.projectsUrl + "/" + projectId)
      .toPromise()
      .then(response => response.json() as Project)
      .catch(this.handleError);
  }

  getSprints(pId: string): Promise<Sprint[]> {
    let projectId = +pId;
    console.log('projectId' + isNaN(projectId));
    if (isNaN(projectId)) {
      return Promise.resolve(null);
    }

    return this.http.get(this.findSprintsByProjectIdUrl + projectId + '&sort=id,desc')
      .toPromise()
      .then(response => response.json()._embedded.sprints as Sprint[])
      .catch(this.handleError);
  }

  getDevelopers(): Promise<Developer[]> {
    return this.http.get(this.developersUrl)
      .toPromise()
      .then(response => response.json()._embedded.developers as Developer[])
      .catch(this.handleError);
  }

  getDeveloper(dId: string): Promise<Developer> {

    let developerId = +dId;
    console.log('developerId' + isNaN(developerId));
    if (isNaN(developerId)) {
      return Promise.resolve(null);
    }

    return this.http.get(this.developersUrl + '/' + developerId)
      .toPromise()
      .then(response => response.json() as Developer)
      .catch(this.handleError);
  }

  getSprintScriptsSnippets(sId): Promise<ScriptSnippet[]> {
    return this.http.get(this.sprintsUrl + "/" + sId + "/scriptSnippets")
      .toPromise()
      .then(response => response.json()._embedded.scriptSnippets as ScriptSnippet[])
      .catch(this.handleError);
  }

  saveNewProject(project: Project): Promise<Project> {
    return this.http
      .post(this.projectsUrl, JSON.stringify(project), { headers: this.headers })
      .toPromise()
      .then(res => res.json() as Project)
      .catch(this.handleError);
  }

  saveNewDeveloper(developer: Developer): Promise<Developer> {
    return this.http
      .post(this.developersUrl, JSON.stringify(developer), { headers: this.headers })
      .toPromise()
      .then(res => res.json() as Developer)
      .catch(this.handleError);
  }

  saveNewSprint(sprint: Sprint): Promise<Sprint> {
    return this.http
      .post(this.sprintsUrl, JSON.stringify(sprint), { headers: this.headers })
      .toPromise()
      .then(res => res.json() as Sprint)
      .catch(this.handleError);
  }

  saveNewScriptSnippet(scriptSnippet: ScriptSnippet): Promise<ScriptSnippet> {
    return this.http
      .post(this.scriptSnippetUrl, JSON.stringify(scriptSnippet), { headers: this.headers })
      .toPromise()
      .then(res => res.json() as ScriptSnippet)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  public getWorkingProjectEmitter():EventEmitter<Project>{
    return this.workingProjectEmitter;
  }

  public getWorkingDeveloperEmitter():EventEmitter<Developer>{
    return this.workingDeveloperEmitter;
  }

  public getWorkingSprintEmitter():EventEmitter<Sprint>{
    return this.workingSprintEmitter;
  }

  public emitWorkingProject(project: Project):void{
    this.getWorkingProjectEmitter().emit(project);
  }

  public emitWorkingDeveloper(developer: Developer):void{
     this.getWorkingDeveloperEmitter().emit(developer);
  }

  public emitWorkingSprint(sprint: Sprint):void{
     this.getWorkingSprintEmitter().emit(sprint);
  }

}
