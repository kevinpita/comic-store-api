/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import io.github.kevinpita.comicstoreapi.creator.CreatorDto;
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

    public static ComicCreatorDto from(ComicCreator comicCreator) {
        return ComicCreatorDto.builder()
                .id(comicCreator.getId())
                .creator(
                        CreatorDto.builder()
                                .id(comicCreator.getCreator().getId())
                                .fullName(comicCreator.getCreator().getFullName())
                                .build())
                .role(comicCreator.getRole())
                .build();
    }
}
