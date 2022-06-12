/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.validation;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/validation")
public class ValidationController {
    @GetMapping
    public ResponseEntity<CustomResponse> checkValidation() {
        return CustomResponse.builder().error(false).message("validated").build().withResponse(200);
    }
}
