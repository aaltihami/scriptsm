package sa.com.elm.tamms;

public class TammScriptsManager {
	public TammScriptsManager(){
	}
	
	public ScriptSnippetType detectScriptSnippetType(String scriptSnippetText){
		return ScriptSnippetType.detectScriptSnippetType(scriptSnippetText);
	}
	
	public String generateScriptSnippetRollback(String scriptSnippetText){
		
		if(scriptSnippetText == null){
			return null;
		}
		
		scriptSnippetText = scriptSnippetText.trim();
		
		ScriptSnippetType scriptSnippetType = detectScriptSnippetType(scriptSnippetText); 
		System.out.println("scriptSnippetType :" + scriptSnippetType);
		ScriptSnippetRollbackGenerator generator;
		try {
			generator = ScriptSnippetRollbackGeneratorFactory.getInstance(scriptSnippetType);
			return generator.getRollbackScript(scriptSnippetText);
		} catch (UnknownScriptSnippetType e) {
		}
		return "\n--Unknown rollback for input "+ scriptSnippetText.replace("\n", " ")  +" \n";
		
	}
}
