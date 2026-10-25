package humanit.clientAPI.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDTO {
    @NotBlank(message = "Document number is required")
    private String number;
    
    private String description;
    
    @NotNull(message = "Expiration date is required")
    private LocalDate expirationDate;
}
