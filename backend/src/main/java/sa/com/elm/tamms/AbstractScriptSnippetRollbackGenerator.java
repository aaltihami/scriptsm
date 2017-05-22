package sa.com.elm.tamms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class AbstractScriptSnippetRollbackGenerator implements ScriptSnippetRollbackGenerator {

	protected HashMap<String, List<String>> foundKeys;
	
	public AbstractScriptSnippetRollbackGenerator(){
		foundKeys = new HashMap<String, List<String>>();
	}
	
	@Override
	public String getRollbackScript(String scriptSnippetText) {
		extractKeys(scriptSnippetText);
		String template = getRollbackTemplate();
		Iterator<Map.Entry<String, List<String>>> keysIterator = foundKeys.entrySet().iterator();
	    while (keysIterator.hasNext()) {
	    	Map.Entry<String, List<String>> pair = (Map.Entry<String, List<String>>)keysIterator.next();
	 
	    	List<String> values = pair.getValue();
	    	if(values.size() == 1){
	    		template = template.replaceAll("<"+ pair.getKey() +">", values.get(0));
	    	}else if(values.size() > 1){
	    		for(String value : values){
	    			template = template.replaceFirst("<"+ pair.getKey() +">", value);
	    		}
	    	}
	    }
	    return template;
	}

	@Override
	public void extractKeys(String scriptSnippetText) {
		scriptSnippetText = scriptSnippetText.replaceAll("^[(--)].*", "");
		Scanner scanner = new Scanner(scriptSnippetText);
		scanner.useDelimiter("\\s");
		
		List<String> keywords = getScriptSnippetType().getKeywords();
		
		for(String keyword : keywords){
			while(scanner.hasNext()){
				if (scanner.next().equalsIgnoreCase(keyword) && scanner.hasNext()){
					String value = scanner.next();
					keyFound(keyword, value);
					continue;
				}
			}
			scanner.close();
			scanner = new Scanner(scriptSnippetText);
		}
		
		scanner.close();
		
	}
	
	@Override
	public void keyFound(String keyword, String value) {
		System.out.println("keyword:"+keyword + " , value:" + value);
		
		if(foundKeys.containsKey(keyword)){
			List<String> values = foundKeys.get(keyword);
			values.add(value);
		}else{
			List<String> values = new ArrayList<String>();
			values.add(value);
			foundKeys.put(keyword, values);
		}
	}
	
	@Override
	public HashMap<String, List<String>> getFoundKeys() {
		return this.foundKeys;
	}
}
