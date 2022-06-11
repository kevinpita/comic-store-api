/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class CustomResponse {
    private final boolean error;
    private final String message;
    private final Object data;

    public ResponseEntity<CustomResponse> withResponse(int status_code) {
        return ResponseEntity.status(status_code).body(this);
    }
}
