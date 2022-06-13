/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/creators")
public class CreatorController {
    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping
    public final ResponseEntity<CustomResponse> getAll() {
        List<Creator> creators = creatorService.getCreators();
        List<CreatorDto> creatorDTOS  = new ArrayList<>();
        for (Creator creator : creators) {
            int countedComics = creatorService.countComics(creator);
            CreatorDto dto = CreatorService.from(creator);
            dto.setCreatedComics(countedComics);
            creatorDTOS.add(dto);
        }

        return CustomResponse.builder()
                .error(false)
                .message("Creators")
                .data(creatorDTOS)
                .build()
                .withResponse(200);

    }
}
