/**
 * Author: dendy
 * Date:07/10/2024
 * Time:8:11
 * Description:
 */

package org.ukdw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
}