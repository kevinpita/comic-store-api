/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.stats;

import io.github.kevinpita.comicstoreapi.collection.CollectionRepository;
import io.github.kevinpita.comicstoreapi.comic.ComicRepository;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopyRepository;
import io.github.kevinpita.comicstoreapi.creator.CreatorRepository;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final CollectionRepository collectionRepository;
    private final ComicRepository comicRepository;
    private final ComicCopyRepository comicCopyRepository;
    private final CreatorRepository creatorRepository;

    public StatsService(
            CollectionRepository collectionRepository,
            ComicRepository comicRepository,
            ComicCopyRepository comicCopyRepository,
            CreatorRepository creatorRepository) {
        this.collectionRepository = collectionRepository;
        this.comicRepository = comicRepository;
        this.comicCopyRepository = comicCopyRepository;
        this.creatorRepository = creatorRepository;
    }

    public Map<String, Long> getStatistics() {
        return Map.of(
                "collections", collectionRepository.count(),
                "comics", comicRepository.count(),
                "comicCopies", comicCopyRepository.count(),
                "creators", creatorRepository.count());
    }
}
