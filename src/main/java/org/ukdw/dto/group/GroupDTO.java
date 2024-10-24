package org.ukdw.dto.group;

import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Optional<String> groupname = Optional.empty();;
    private Optional<Long> permission = Optional.empty();;
}
