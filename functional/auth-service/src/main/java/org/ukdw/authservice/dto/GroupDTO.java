package org.ukdw.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    @NotBlank(message = "Groupname is required")
    private String groupname;

    private Optional<Long> permission = Optional.empty();
}
