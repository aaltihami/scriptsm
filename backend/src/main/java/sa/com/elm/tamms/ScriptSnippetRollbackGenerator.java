package sa.com.elm.tamms;

import java.util.HashMap;
import java.util.List;

public interface ScriptSnippetRollbackGenerator {
	 String getRollbackScript(String scriptSnippetText);
	 String getRollbackTemplate();
	 void extractKeys(String scriptSnippetText);
	 ScriptSnippetType getScriptSnippetType();
	 void keyFound(String key , String value);
	 HashMap<String, List<String>> getFoundKeys();
}
