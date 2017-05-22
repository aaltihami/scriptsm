package sa.com.elm.tamms;

public interface ScriptSnippetRollbackGeneratorFactory {
	
	public static ScriptSnippetRollbackGenerator getInstance(ScriptSnippetType scriptSnippetType) throws UnknownScriptSnippetType {
		switch(scriptSnippetType){
			case CREATE_TABLE:
				return new CreateTableRollbackGenerator();
			case CREATE_PROCEDURE:
				return new CreateProcedureRollbackGenerator();
			case ALTER_PROCEDURE:
				return new AlterProcedureRollbackGenerator();
			case ALTER_TABLE_ADD_COLUMN:
				return new AlterTableAddColumnRollbackGenerator();
			case INSERT:
				return new InsertRollbackGenerator();
			case UNKNOWN:
			defualt: 
				throw new UnknownScriptSnippetType("");
		}
		return null;
	}

}
