/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.configuration;

import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.collection.CollectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "service.mock", havingValue = "true")
public class CollectionCommandLineRunner implements CommandLineRunner {
    private final CollectionRepository collectionRepository;

    public CollectionCommandLineRunner(CollectionRepository repository) {
        this.collectionRepository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Collection collection = new Collection();
        collection.setName("Marvel 1");
        collection.setDescription("The Marvel Universe");
        collection.setPublisher("Marvel Comics");
        collectionRepository.save(collection);
    }
}
