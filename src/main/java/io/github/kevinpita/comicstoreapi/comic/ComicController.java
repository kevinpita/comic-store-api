/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comics")
public class ComicController {
    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public List<Comic> getComics() {
        return comicService.getAllComics();
    }

    @PostMapping
    public Comic createComic(@Valid @RequestBody Comic comic) {
        return comicService.createComic(comic);
    }
}
