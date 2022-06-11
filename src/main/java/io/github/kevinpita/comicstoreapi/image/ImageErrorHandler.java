/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.image;

import io.github.kevinpita.comicstoreapi.image.exception.InvalidModelIdException;
import io.github.kevinpita.comicstoreapi.image.exception.ModelNotFoundException;
import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageErrorHandler {
    @ExceptionHandler(InvalidModelIdException.class)
    public ResponseEntity<CustomResponse> handleInvalidModelIdException(InvalidModelIdException e) {
        return CustomResponse.builder()
                .error(true)
                .message(e.getMessage())
                .data(null)
                .build()
                .withResponse(404);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomResponse> handleModelNotFoundException(ModelNotFoundException e) {
        return CustomResponse.builder()
                .error(true)
                .message(e.getMessage())
                .data(null)
                .build()
                .withResponse(400);
    }
}
