/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopy;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorDto;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ComicService {
    private final ComicRepository comicRepository;

    public ComicService(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    public Comic getComic(Long id) {
        return comicRepository.findById(id).orElse(null);
    }

    public Comic createComic(Comic comic) {
        return comicRepository.save(comic);
    }

    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    public static ComicDto from(Comic comic) {
        ComicDto.CollectionDto collection =
                ComicDto.CollectionDto.builder()
                        .id(comic.getCollection().getId())
                        .name(comic.getCollection().getName())
                        .build();

        List<ComicCreatorDto> comicCreators =
                comic.getComicCreators().stream()
                        .map(ComicCreatorService::from)
                        .collect(Collectors.toList());

        List<ComicDto.ComicCopyDto> copies =
                comic.getCopies().stream().map(ComicService::fromCopy).collect(Collectors.toList());

        return ComicDto.builder()
                .id(comic.getId())
                .title(comic.getTitle())
                .description(comic.getDescription())
                .issueNumber(comic.getIssueNumber())
                .collection(collection)
                .comicCreators(comicCreators)
                .copies(copies)
                .build();
    }

    public static ComicDto.ComicCopyDto fromCopy(ComicCopy comicCopy) {
        return ComicDto.ComicCopyDto.builder()
                .id(comicCopy.getId())
                .purchaseDate(comicCopy.getPurchaseDate())
                .state(comicCopy.getState())
                .price(comicCopy.getPrice())
                .cover(comicCopy.getCover())
                .build();
    }
}
