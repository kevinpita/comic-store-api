/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccopy;

import org.springframework.stereotype.Service;

@Service
public class ComicCopyService {
    private final ComicCopyRepository comicCopyRepository;

    public ComicCopyService(ComicCopyRepository comicCopyRepository) {
        this.comicCopyRepository = comicCopyRepository;
    }

    public ComicCopy create(ComicCopy comicCopy) {
        return comicCopyRepository.save(comicCopy);
    }

    public ComicCopy update(ComicCopy comicCopy) {
        return comicCopyRepository.save(comicCopy);
    }

    public void delete(Long id) {
        comicCopyRepository.deleteById(id);
    }
}
