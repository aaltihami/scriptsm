package sa.com.elm.tamms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertRollbackGenerator extends AbstractScriptSnippetRollbackGenerator {

	private List<String> columnsNames;
	private List<String> values;

	@Override
	public String getRollbackScript(String scriptSnippetText) {
		String delete = super.getRollbackScript(scriptSnippetText);
		String where = " WHERE " ;
		for (int i = 0; i < columnsNames.size(); i++) {
			
			if(values.get(i).contains("(")){
				where += "\n--";
			}
			
			if(i > 0){
				where += " AND ";	
			}
			
			where += (columnsNames.get(i) + "=" + (values.size() > i? values.get(i) : "")) + "\n";
		}
		return  delete + "\n" +  where + "\nGO\n ";
	}

	@Override
	public String getRollbackTemplate() {
		return "DELETE <INTO>";
	}

	@Override
	public void extractKeys(String scriptSnippetText) {
		super.extractKeys(scriptSnippetText);
		scriptSnippetText = scriptSnippetText.replaceAll("^[(--)].*", "");

		String columnsAndValues[] = scriptSnippetText.split("(?i)\\bVALUES\\b");

		extraxtColumnsNames(columnsAndValues[0]);
		extraxtColumnsValues(columnsAndValues[1]);

	}

	protected void extraxtColumnsNames(String columnsString) {
		columnsString = columnsString.substring(columnsString.indexOf("(") + 1, columnsString.lastIndexOf(")"));
		System.out.println("columnsString " + columnsString);
		String[] columnsNamesArr = columnsString.split(",");
		System.out.println("columnsNamesArr " + columnsNamesArr);
		this.columnsNames = Arrays.asList(columnsNamesArr);
	}

	protected void extraxtColumnsValues(String valueString) {
		valueString = valueString.substring(valueString.indexOf("(") + 1, valueString.lastIndexOf(")")).trim();
		System.out.println("valueString " + valueString);
		
		values = new ArrayList<String>();

		char[] charArray = valueString.toCharArray();
		String value = "";
		for (int i = 0; i < charArray.length; i++) {
			try {
				do {
					if (charArray[i] == '(') {
						while (charArray[i] != ')') {
							value += charArray[i++];
						}
					} else {
						value += charArray[i++];
					}
				} while (i < charArray.length && charArray[i] != ',');
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}

			System.out.println("value :" + value);
			values.add(value);
			value = "";
		}
	}

	@Override
	public ScriptSnippetType getScriptSnippetType() {
		return ScriptSnippetType.INSERT;
	}

	public static void main(String[] args) {
		InsertRollbackGenerator i = new InsertRollbackGenerator();
		i.getRollbackScript(
				"INSERT INTO dbo.Points (PointValue) VALUES (CONVERT(Point, '1,5') ,N'ينبانتسا' , efsdfsdf , getdate() , fjjj('f' , 'rrr'))");
	}

}
