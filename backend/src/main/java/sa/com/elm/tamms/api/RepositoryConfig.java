package sa.com.elm.tamms.api;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import sa.net.elm.tamms.api.entities.Developer;
import sa.net.elm.tamms.api.entities.Project;
import sa.net.elm.tamms.api.entities.ScriptSnippet;
import sa.net.elm.tamms.api.entities.Sprint;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Developer.class);
        config.exposeIdsFor(Project.class);
        config.exposeIdsFor(Sprint.class);
        config.exposeIdsFor(ScriptSnippet.class);
        
        config.setBasePath("tamms");
    }
}