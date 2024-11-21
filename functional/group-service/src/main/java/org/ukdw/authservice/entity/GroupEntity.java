package org.ukdw.authservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name = "groups")
public class GroupEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_SEQ")
    @SequenceGenerator(name = "GROUP_SEQ", sequenceName = "GROUP_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "groupname", nullable = false)
    private String groupname;

//    @ManyToMany(mappedBy = "groups")
//    @JsonBackReference // Prevent recursion by indicating this is the "back" side of the relationship
//    private Set<UserAccountEntity> users = new HashSet<>();

    // store roles and permission as a bitmask type
    @Column(name = "permission", columnDefinition = "BIGINT DEFAULT 0")
    private long permission;

    /**
     * Get resources based on permissions.
     * @return Map of permission bit values to names.
     */
    public Map<Long, String> getResources() {
        return ResourceConstants.loadResourceNames(this.permission);
    }

    /**
     * <p>Add roles/permissions using bitwise OR </p>
     * @param permission bitmask representation of role / permission
     */
    // Add roles/permissions using bitwise OR
    public void addRoleOrPermission(long permission) {
        this.permission |= permission;
    }

    /**
     * <p>Remove roles/permissions using the bitwise AND of the permission's negation  </p>
     * @param permission bitmask representation of role / permission
     */
    // Add roles/permissions using bitwise OR
    public void removeRoleOrPermission(long permission) { this.permission &= ~permission; }

    /**
     * <p>Check if a specific permission is present using bitwise AND </p>
     * @param permission bitmask representation of role / permission
     */
    public boolean hasPermission(long permission) {
        if (permission >= this.permission){
            return (this.permission & permission) == permission;
        }else{
            return (this.permission & permission) != 0;
        }
    }
}
