package sa.com.elm.tamms;

public class CreateTableRollbackGenerator extends AbstractScriptSnippetRollbackGenerator{
	

	@Override
	public String getRollbackTemplate() {
		return " IF OBJECT_ID('<TABLE>', 'U') IS NOT NULL \n" + 
			   " BEGIN \n" +
			   " DROP TABLE <TABLE> \n " + 
			   " END \n"+
			   " GO \n";
	}
	
	@Override
	public ScriptSnippetType getScriptSnippetType() {
		return ScriptSnippetType.CREATE_TABLE;
	}
}
