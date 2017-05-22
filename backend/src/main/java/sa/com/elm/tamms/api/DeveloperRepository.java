package sa.com.elm.tamms.api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sa.net.elm.tamms.api.entities.Developer;
import sa.net.elm.tamms.api.entities.Project;
@RepositoryRestResource(itemResourceRel="developer" ,collectionResourceRel = "developers", path = "developers")
public interface DeveloperRepository extends PagingAndSortingRepository<Developer, Long> {
}