package org.ukdw.authservice.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Optional<String> groupname = Optional.empty();;
    private Optional<Long> permission = Optional.empty();;
}
