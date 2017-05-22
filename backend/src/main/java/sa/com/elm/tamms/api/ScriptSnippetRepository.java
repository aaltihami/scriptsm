package sa.com.elm.tamms.api;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sa.net.elm.tamms.api.entities.ScriptSnippet;
@RepositoryRestResource(itemResourceRel="scriptSnippet" ,collectionResourceRel = "scriptSnippets", path = "scriptSnippets")
public interface ScriptSnippetRepository extends PagingAndSortingRepository<ScriptSnippet, Long> {
	public List<ScriptSnippet> findBySprintId(@Param("sprintId") long sprintId);
}