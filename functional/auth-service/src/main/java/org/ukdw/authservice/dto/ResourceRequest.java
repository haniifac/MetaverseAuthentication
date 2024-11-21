package org.ukdw.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {
    private Long resourceId;
    private String resourceName;
    private Long bitwiseShift;
}
