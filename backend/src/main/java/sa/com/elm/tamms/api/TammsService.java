package sa.com.elm.tamms.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sa.net.elm.tamms.TammScriptsManager;
import sa.net.elm.tamms.api.entities.ScriptSnippet;

@Service
public class TammsService {
	
	private TammScriptsManager tammScriptsManager;
	private ScriptSnippetRepository scriptSnippetRepository;
	
	public List<ScriptSnippet> getSprintScripts(long sprintId){
		return scriptSnippetRepository.findBySprintId(sprintId);
	}
	
	public String generateScriptSnippetRollback(String scriptSnippetText){
		return tammScriptsManager.generateScriptSnippetRollback(scriptSnippetText);
	}
	
	@Autowired
	public void setTammScriptsManager(TammScriptsManager tammScriptsManager) {
		this.tammScriptsManager = tammScriptsManager;
	}
	
	public TammScriptsManager getTammScriptsManager() {
		return tammScriptsManager;
	}
	
	public ScriptSnippetRepository getScriptSnippetRepository() {
		return scriptSnippetRepository;
	}
	
	@Autowired
	public void setScriptSnippetRepository(ScriptSnippetRepository scriptSnippetRepository) {
		this.scriptSnippetRepository = scriptSnippetRepository;
	}
	
	

	
	public static void main(String[] args) {
		String s = "ALTER ";
		System.out.println(s.matches("(?i)\\bALTER\\b"));
	}
}
