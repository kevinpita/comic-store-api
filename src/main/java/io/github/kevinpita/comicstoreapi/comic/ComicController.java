/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comics")
public class ComicController {
    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getComics() {
        List<Comic> comics = comicService.getAllComics();
        List<ComicDto> data = comics.stream().map(ComicService::from).collect(Collectors.toList());
        return CustomResponse.builder()
                .error(false)
                .message("Comics")
                .data(data)
                .build()
                .withResponse(200);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getComic(@PathVariable Long id) {
        Comic comics = comicService.getComic(id);
        ComicDto data = ComicService.from(comics);
        return CustomResponse.builder()
                .error(false)
                .message("Comics")
                .data(data)
                .build()
                .withResponse(200);
    }

    @PostMapping
    public final ResponseEntity<CustomResponse> create(@RequestBody ComicDto comicDto) {
        Comic comic = comicService.createComic(comicDto);
        ComicDto data = ComicService.from(comic);
        return CustomResponse.builder()
                .error(false)
                .message("Creator created")
                .data(data)
                .build()
                .withResponse(201);
    }
}
