package org.ukdw.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupPermissionRequest {
    @NotNull(message = "group id is required")
    private Long groupId;

    @NotNull(message = "permission is required")
    private Long permission;
}
