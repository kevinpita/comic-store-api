/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import org.springframework.stereotype.Service;

@Service
public class CreatorService {
    private final CreatorRepository repository;

    public CreatorService(CreatorRepository repository) {
        this.repository = repository;
    }

    public Iterable<Creator> findAll() {
        return repository.findAll();
    }

    public Creator findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Creator save(Creator creator) {
        return repository.save(creator);
    }

    public void delete(Creator creator) {
        repository.delete(creator);
    }
}
