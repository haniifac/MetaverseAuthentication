package org.ukdw.dto.request.auth;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


/**

 * Creator: haniif
 * Date: 10/26/2024
 * Time: 8:05 PM
 *
 * Description : Add / remove permission from user
 */
@Setter
@Getter
public class GroupPermissionRequest {
    @NotNull(message = "group id is required")
    private long groupId;

    @NotNull(message = "feature code is required")
    private long featureCode;
}
