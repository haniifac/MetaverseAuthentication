package org.ukdw.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    @NotBlank(message = "Resource name is required")
    private String resourceName;

    @NotNull(message = "Resource bit shift 2^(x) is required")
    private Long resourceShift;
}
