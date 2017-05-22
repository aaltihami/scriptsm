package sa.com.elm.tamms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ScriptSnippet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name="sprint_id")
	private Sprint sprint;
	@Column(length=10000)
	private String snippetDeploy;
	@Column(length=10000)
	private String snippetRollback;
	
	public ScriptSnippet(){
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public String getSnippetDeploy() {
		return snippetDeploy;
	}
	public void setSnippetDeploy(String snippetDeploy) {
		this.snippetDeploy = snippetDeploy;
	}
	public String getSnippetRollback() {
		return snippetRollback;
	}
	public void setSnippetRollback(String snippetRollback) {
		this.snippetRollback = snippetRollback;
	}

	@Override
	public String toString() {
		return "ScriptSnippet [id=" + id + ", sprint=" + sprint + ", snippetDeploy=" + snippetDeploy
				+ ", snippetRollback=" + snippetRollback + "]";
	}
	
}
