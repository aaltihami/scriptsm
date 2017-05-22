package sa.com.elm.tamms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ScriptSnippetType {
	/*the order matters*/
	CREATE_PROCEDURE(2,Keywords.CREATE_PROCEDURE_KEYWORDS , "CREATE,PROCEDURE"),
	ALTER_PROCEDURE(4,Keywords.ALTER_PROCEDURE_KEYWORDS , "ALTER,PROCEDURE"),
	CREATE_TABLE(1, Keywords.CREATE_TABLE_KEYWORDS , "CREATE,TABLE"),
	ALTER_TABLE_ADD_COLUMN(3,Keywords.ALTER_TABLE_ADD_COLUMN_KEYWORDS ,"ALTER,TABLE,ADD"),
	INSERT(5,Keywords.INSERT_KEYWORDS ,"INSERT,INTO"),
	UNKNOWN(-1, new ArrayList<String>(), "");
	
	
	
	private int code;
	private List<String> keywords;
	private String identifiers;
	
	private ScriptSnippetType(int code, List<String> keywords , String identifier){
		this.code = code;
		this.keywords = keywords;
		this.identifiers = identifier;
	}
	
	public int getCode(){
		return code;
	}
	
	public List<String> getKeywords(){
		return keywords;
	}
	
	public String getIdentifiers() {
		return identifiers;
	}
	
	public void setIdentifiers(String identifiers) {
		this.identifiers = identifiers;
	}
	
	public static ScriptSnippetType detectScriptSnippetType(String scriptSnippetText){

		scriptSnippetText = scriptSnippetText.replaceAll("^[(--)].*", "");
		System.out.println("scriptSnippetText nocomments:" + scriptSnippetText);
		
		
		types:for(ScriptSnippetType snippetType : ScriptSnippetType.values()){
			if(snippetType == UNKNOWN){
				continue;
			}
			
			String[] identifiersArray =  snippetType.getIdentifiers().split(",");
			
			int startIndex = 0;
			for (int i = 0; i < identifiersArray.length; i++) {
				int resultIndex = scriptSnippetText.toUpperCase(). indexOf(identifiersArray[i].toUpperCase(), startIndex);
				
				if(resultIndex < 0){
					continue types;
				}else{
					startIndex = resultIndex;
				}
			}
			
			return snippetType;
		}
		return UNKNOWN;
	}
	
	static class Keywords{
		static final List<String> CREATE_TABLE_KEYWORDS = Arrays.asList(new String[]{"TABLE"});
		static final List<String> CREATE_PROCEDURE_KEYWORDS = Arrays.asList(new String[]{"PROCEDURE"});
		static final List<String> ALTER_TABLE_ADD_COLUMN_KEYWORDS = Arrays.asList(new String[]{"TABLE" , "ADD", "CONSTRAINT"});
		static final List<String> ALTER_PROCEDURE_KEYWORDS = Arrays.asList(new String[]{"PROCEDURE"});
		static final List<String> INSERT_KEYWORDS = Arrays.asList(new String[]{"INTO"});
	}
}



