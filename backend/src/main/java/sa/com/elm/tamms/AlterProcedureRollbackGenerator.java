package sa.com.elm.tamms;

public class AlterProcedureRollbackGenerator extends AbstractScriptSnippetRollbackGenerator{

	@Override
	public String getRollbackScript(String scriptSnippetText) {
		String[] scripts = scriptSnippetText.split("#procedure-rollbak# ");
		return scripts.length > 1 ? scripts[1] + "\nGO\n" : "Rollback procedure not fond";
	}

	@Override
	public String getRollbackTemplate() {
		return null;
	}

	@Override
	public ScriptSnippetType getScriptSnippetType() {
		return ScriptSnippetType.ALTER_PROCEDURE;
	}
	
	

}
