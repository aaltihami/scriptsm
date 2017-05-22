package sa.com.elm.tamms;

public class CreateProcedureRollbackGenerator extends AbstractScriptSnippetRollbackGenerator{

	@Override
	public String getRollbackTemplate() {
		return  " IF EXISTS ( SELECT  *  \n" + 
				" FROM    sys.objects \n" +
				" WHERE   object_id = OBJECT_ID(N'<PROCEDURE>') \n" +
                "         AND type IN ( N'P', N'PC' ) ) \n " +
				" BEGIN \n" +
                	" DROP PROCEDURE <PROCEDURE> \n" +
                " END \n " +
				" GO \n";
	}

	@Override
	public ScriptSnippetType getScriptSnippetType() {
		return ScriptSnippetType.CREATE_PROCEDURE;
	}

}
