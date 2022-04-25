/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import org.springframework.stereotype.Service;

@Service
public class ComicCreatorService {
    private final ComicCreatorRepository comicCreatorRepository;

    public ComicCreatorService(ComicCreatorRepository comicCreatorRepository) {
        this.comicCreatorRepository = comicCreatorRepository;
    }

    public ComicCreator save(ComicCreator comicCreator) {
        return comicCreatorRepository.save(comicCreator);
    }

    public ComicCreator findById(Long id) {
        return comicCreatorRepository.findById(id).orElse(null);
    }

    public Iterable<ComicCreator> findAll() {
        return comicCreatorRepository.findAll();
    }

    public void deleteById(Long id) {
        comicCreatorRepository.deleteById(id);
    }
}
