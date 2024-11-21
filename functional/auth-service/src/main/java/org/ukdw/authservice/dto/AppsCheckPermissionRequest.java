package org.ukdw.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
    private long featureCode;
}
