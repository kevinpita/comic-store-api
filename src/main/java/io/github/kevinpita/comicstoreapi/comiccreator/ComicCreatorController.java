/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comiccreator;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComicCreatorController {
    private final ComicCreatorService comicCreatorService;

    public ComicCreatorController(ComicCreatorService comicCreatorService) {
        this.comicCreatorService = comicCreatorService;
    }
}
