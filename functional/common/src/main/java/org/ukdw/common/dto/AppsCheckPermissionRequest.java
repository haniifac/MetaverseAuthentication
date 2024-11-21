package org.ukdw.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**

 * Creator: dendy
 * Date: 7/11/2024
 * Time: 12:55 PM
 *
 * Description : CheckPermissionRequest
 */
@Setter
@Getter
public class AppsCheckPermissionRequest {
    @NotNull(message = "feature code is required")
    private Long[] permissions;

    @NotNull(message = "role is required")
    private String[] roles;
}
