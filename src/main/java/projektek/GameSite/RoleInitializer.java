package projektek.GameSite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import projektek.GameSite.models.data.user.Role;
import projektek.GameSite.models.repositories.user.RoleRepository;

@Component
public class RoleInitializer {
    private final RoleRepository repository;

    @Autowired
    public RoleInitializer(RoleRepository repository) {
        this.repository = repository;
    }

    @Bean
    CommandLineRunner initializeRoles() {
        return args -> {
            if (repository.findByAuthority("ROLE_USER") == null) {
                repository.save(new Role("ROLE_USER"));
            }
            if (repository.findByAuthority("ROLE_ADMIN") == null) {
                repository.save(new Role("ROLE_ADMIN"));
            }
        };
    }
}
