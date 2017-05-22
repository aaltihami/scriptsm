package sa.com.elm.tamms.api;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import sa.net.elm.tamms.api.entities.Sprint;
@RepositoryRestResource(itemResourceRel="sprint" ,collectionResourceRel = "sprints", path = "sprints")
public interface SprintRepository extends PagingAndSortingRepository<Sprint, Long> {
	@RestResource(path="findByProjectId" , rel="findByProjectId")
	public List<Sprint> findByProjectId(@Param("projectId") long projectId, Pageable pageable);
}