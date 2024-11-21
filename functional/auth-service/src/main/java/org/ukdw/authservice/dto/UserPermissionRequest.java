package org.ukdw.authservice.dto;

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

@Getter
@Setter
public class UserPermissionRequest {
    @NotNull(message = "user id is required")
    private long userId;

    @NotNull(message = "group id is required")
    private long groupId;
}
