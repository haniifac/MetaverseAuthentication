/**
 * Author: dendy
 * Date:03/10/2024
 * Time:9:52
 * Description: group
 */

package org.ukdw.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "groups")
public class GroupEntity implements Serializable {
    @Id
    private Long id;

    @Column(name = "groupname", nullable = false)
    private String groupname;

    @ManyToMany(mappedBy = "groups")
    @JsonBackReference // Prevent recursion by indicating this is the "back" side of the relationship
    private Set<UserAccountEntity> users = new HashSet<>();
}
