package sa.com.elm.tamms.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import sa.net.elm.tamms.api.entities.ScriptSnippet;

@Component
@RepositoryEventHandler(ScriptSnippet.class)
public class AgentEventHandler {
	

	private TammsService tammsService;

    @HandleBeforeCreate(ScriptSnippet.class)
    public void handleBeforeCreate(ScriptSnippet scriptSnippet) {
    	scriptSnippet.setSnippetDeploy(scriptSnippet.getSnippetDeploy().trim());
        System.out.println("Saving scriptSnippet " + scriptSnippet.toString());
        String generateScriptSnippetRollback = tammsService.generateScriptSnippetRollback(scriptSnippet.getSnippetDeploy());
        scriptSnippet.setSnippetRollback(generateScriptSnippetRollback);
        scriptSnippet.setSnippetDeploy(generateScriptSnippetRollback + "\n GO \n" + scriptSnippet.getSnippetDeploy() + " \n GO \n");
        System.out.println("Saving scriptSnippet After:" + scriptSnippet.toString());
    }
    
    @Autowired
    public void setTammsService(TammsService tammsService) {
		this.tammsService = tammsService;
	}
}