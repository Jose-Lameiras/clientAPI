package humanit.clientAPI.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {
    private String firstName;
    private String lastName;
    private String taxIdentifier;
    private String email;
    private String phoneNumber;
    private List<DocumentRequestDTO> documents;
}
