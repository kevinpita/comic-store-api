/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.stats;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getStatistics() {
        return CustomResponse.builder()
                .error(false)
                .message("Statistics")
                .data(statsService.getStatistics())
                .build()
                .withResponse(200);
    }
}
