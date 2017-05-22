package sa.com.elm.tamms;

import java.util.List;

public class AlterTableAddColumnRollbackGenerator extends AbstractScriptSnippetRollbackGenerator{
	

	
	@Override
	public String getRollbackScript(String scriptSnippetText) {
		String dropColumn =  super.getRollbackScript(scriptSnippetText);
		String dropConstraints = getDropConstraintsScript();
		
		return dropConstraints + dropColumn ;
	}
	
	protected String getDropConstraintsScript() {
		String constarintsDrops = "";
		List<String> constraintsNames = foundKeys.get("CONSTRAINT");
		if(constraintsNames == null || constraintsNames.isEmpty()){
			return constarintsDrops;
		}
		
		
		String tableName = foundKeys.get("TABLE") != null && foundKeys.get("TABLE").size() > 0 ? foundKeys.get("TABLE").get(0) : null;
		
		for (String constraintsName : constraintsNames) {
			String dropConstraintTemplate = getDropConstraintTemplate();
			dropConstraintTemplate = dropConstraintTemplate.replaceAll("<TABLE>", tableName);
			dropConstraintTemplate = dropConstraintTemplate.replaceAll("<CONSTRAINT>", constraintsName);
			
			constarintsDrops += dropConstraintTemplate  + "\nGO\n";
		}
		
		return constarintsDrops;
	}

	@Override
	public String getRollbackTemplate() {
		return  " IF COL_LENGTH('<TABLE>', '<ADD>') IS NOT NULL \n" +
				" BEGIN \n" +
				" ALTER TABLE <TABLE> DROP COLUMN <ADD> \n " +
				" END \nGO\n";
	}
	
	
	public String getDropConstraintTemplate() {
		return "IF EXISTS (SELECT OBJECT_NAME(OBJECT_ID) AS NameofConstraint \n" +
					" ,SCHEMA_NAME(schema_id) AS SchemaName \n" +
                    " ,OBJECT_NAME(parent_object_id) AS TableName \n"+
                    "  ,type_desc AS ConstraintType \n" +
                    " FROM sys.objects \n" +
                    " WHERE type_desc LIKE '%CONSTRAINT' \n "+
                    " AND OBJECT_NAME(OBJECT_ID)='<CONSTRAINT>') \n" + 
                    " BEGIN \n"+
                    " ALTER TABLE <TABLE> DROP CONSTRAINT <CONSTRAINT> \n" +
                    " END \nGO\n";
	}


	@Override
	public ScriptSnippetType getScriptSnippetType() {
		return ScriptSnippetType.ALTER_TABLE_ADD_COLUMN;
	}
}
