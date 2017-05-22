package sa.com.elm.tamms.api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sa.net.elm.tamms.api.entities.Project;
@RepositoryRestResource(itemResourceRel="project" ,collectionResourceRel = "projects", path = "projects")
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}