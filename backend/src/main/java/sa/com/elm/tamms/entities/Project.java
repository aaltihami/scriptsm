package sa.com.elm.tamms.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="project_id")
	private long id;
	
	@NotEmpty
	@Column(unique = true, nullable=false)
	private String name;
	
	@OneToMany(mappedBy = "project")
	private List<Sprint> sprints;
	
	public Project() {
	}
	
	public Project(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Sprint> getSprints() {
		return sprints;
	}
	
	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}
}
