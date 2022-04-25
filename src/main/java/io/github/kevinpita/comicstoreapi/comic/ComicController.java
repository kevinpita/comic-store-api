/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComicController {
    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }
}
