package sa.com.elm.tamms.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sa.net.elm.tamms.api.entities.ScriptSnippet;

@RestController()
@RequestMapping("/tamms")
public class TammsController {

	private TammsService tammsService; 
	
    @RequestMapping(method=RequestMethod.GET, path="/sprintScriptsFiles/{sprintId}")
    public void getProjects(@PathVariable("sprintId") long sprintId, HttpServletResponse response){
    	try {
    		
    		
    		List<ScriptSnippet> sprintScripts = tammsService.getSprintScripts(sprintId);
    		StringBuffer sprintDeployemt = new StringBuffer();
    		StringBuffer sprintRollback = new StringBuffer();
    		for(ScriptSnippet scriptSnippet : sprintScripts){
    			sprintDeployemt.append(scriptSnippet.getSnippetDeploy());
    			sprintRollback.append(scriptSnippet.getSnippetRollback());
    		}
    		
    		response.setStatus(HttpServletResponse.SC_OK);
    	    response.addHeader("Content-Disposition", "attachment; filename=\"Sprint_"+sprintId+"_Scripts.zip\"");
    	    ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

  	        ByteArrayInputStream deploumentIs = new ByteArrayInputStream(sprintDeployemt.toString().getBytes());
  	        ZipEntry deploumentEntry = new ZipEntry("Deployment_"+sprintId+".sql");
  	        zipOutputStream.putNextEntry(deploumentEntry);
  	        org.apache.commons.io.IOUtils.copy(deploumentIs, zipOutputStream);
  	        zipOutputStream.flush();
  	        zipOutputStream.closeEntry();
  	       
  	        ByteArrayInputStream rollbackIs = new ByteArrayInputStream(sprintRollback.toString().getBytes());
	        ZipEntry rollbackEntry = new ZipEntry("Rollback_"+sprintId+".sql");
	        zipOutputStream.putNextEntry(rollbackEntry);
	        org.apache.commons.io.IOUtils.copy(rollbackIs, zipOutputStream);
	        zipOutputStream.flush();
  	        zipOutputStream.closeEntry();
  	        
  	        zipOutputStream.close();
	    } catch (IOException ex) {
	     throw new RuntimeException("IOError writing file to output stream");
	    }
    }
    
    
    @Autowired
    public void setTammsService(TammsService tammsService) {
		this.tammsService = tammsService;
	}
    
    public TammsService getTammsService() {
		return tammsService;
	}
}
