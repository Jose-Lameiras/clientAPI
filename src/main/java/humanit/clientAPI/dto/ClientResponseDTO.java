package humanit.clientAPI.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String taxIdentifier;
    private String email;
    private String phoneNumber;
    private List<DocumentResponseDTO> documents;
}
