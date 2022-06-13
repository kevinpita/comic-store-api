/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.configuration;

import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.collection.CollectionRepository;
import io.github.kevinpita.comicstoreapi.comic.Comic;
import io.github.kevinpita.comicstoreapi.comic.ComicRepository;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopy;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopyRepository;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreator;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorRepository;
import io.github.kevinpita.comicstoreapi.creator.Creator;
import io.github.kevinpita.comicstoreapi.creator.CreatorRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "service.mock", havingValue = "true")
public class CollectionCommandLineRunner implements CommandLineRunner {
    private final CollectionRepository collectionRepository;
    private final CreatorRepository creatorRepository;
    private final ComicRepository comicRepository;
    private final ComicCreatorRepository comicCreatorRepository;
    private final ComicCopyRepository comicCopyRepository;

    public CollectionCommandLineRunner(
            CollectionRepository collectionRepository,
            CreatorRepository creatorRepository,
            ComicRepository comicRepository,
            ComicCreatorRepository comicCreatorRepository,
            ComicCopyRepository comicCopyRepository) {
        this.collectionRepository = collectionRepository;
        this.creatorRepository = creatorRepository;
        this.comicRepository = comicRepository;
        this.comicCreatorRepository = comicCreatorRepository;
        this.comicCopyRepository = comicCopyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Collection collection = new Collection();
        collection.setName("DC 1");
        collection.setDescription("The DC Universe");
        collection.setPublisher("DC Comics");
        collectionRepository.save(collection);

        collection = new Collection();
        collection.setName("Marvel 1");
        collection.setDescription("The Marvel Universe");
        collection.setPublisher("Marvel Comics");
        collectionRepository.save(collection);

        Creator creator = new Creator();
        Creator creator2 = new Creator();
        creator.setName("Lee");
        creator.setLastName("Lee");
        creator2.setName("Jack");
        creator2.setLastName("Kirby");
        creatorRepository.save(creator);
        creatorRepository.save(creator2);

        Comic comic = new Comic();
        comic.setTitle("The Amazing Spider");
        comic.setDescription("The Amazing Spider");
        comic.setCollection(collection);
        comic.setIssueNumber(1);
        comicRepository.save(comic);

        ComicCreator comicCreator = new ComicCreator();
        comicCreator.setRole("Escritor");
        comicCreator.setCreator(creator);
        comicCreator.setComic(comic);
        comicCreatorRepository.save(comicCreator);

        ComicCreator comicCreator2 = new ComicCreator();
        comicCreator2.setRole("dibujante");
        comicCreator2.setCreator(creator2);
        comicCreator2.setComic(comic);
        comicCreatorRepository.save(comicCreator2);

        ComicCopy comicCopy = new ComicCopy();
        comicCopy.setComic(comic);
        comicCopy.setPrice(BigDecimal.valueOf(850.0));
        comicCopy.setState("Nuevo");
        comicCopy.setCover("Blanda");
        comicCopy.setPurchaseDate(LocalDate.now());
        comicCopyRepository.save(comicCopy);
    }
}
