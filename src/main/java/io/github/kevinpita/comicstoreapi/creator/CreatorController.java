/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatorController {
    private final CreatorRepository creatorRepository;

    public CreatorController(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }
}
