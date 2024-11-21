package org.ukdw.authservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Creator: dendy
 * Date: 7/11/2024
 * Time: 12:52 PM
 */

@Data
@Builder
public class AppsCheckPermissionResponse {
    private boolean status;
}
