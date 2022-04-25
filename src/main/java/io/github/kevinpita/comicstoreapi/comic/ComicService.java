/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

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
}
