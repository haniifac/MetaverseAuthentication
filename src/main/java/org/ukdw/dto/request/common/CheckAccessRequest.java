package org.ukdw.dto.request.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckAccessRequest {
    private String username;
    private long permissionId;
}
