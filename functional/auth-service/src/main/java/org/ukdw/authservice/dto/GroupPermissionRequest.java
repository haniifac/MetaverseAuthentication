package org.ukdw.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupPermissionRequest {
    @NotNull(message = "group id is required")
    private long groupId;

    @NotNull(message = "feature code is required")
    private long featureCode;
}
