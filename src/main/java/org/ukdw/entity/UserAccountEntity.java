package org.ukdw.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity(name = "user_account")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 100)
    private long id;

    @Column(name = "username", length = 50, unique = true)
    @Setter
    @Getter
    private String username;

    @Column(name = "password", length = 40)
    @Setter
    @Getter
    private String password;

    private String accessToken;

    private String idToken;

    /*The refresh_token is only returned on the first request.
     When you refresh the access token a second time it returns everything except the refresh_token and the file_put_contents
      removes the refresh_token when this happens the second time.
      You will get refreshToken if user access to google API already revoked. */
    private String refreshToken;

    private String fcmToken;

    private String regNumber;

    private String email;

    private String imageUrl;

//    private String role;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonManagedReference // Prevent recursion by indicating this is the "forward" side of the relationship
    private Set<GroupEntity> groups = new HashSet<>();

    public UserAccountEntity() {}

    public UserAccountEntity(
            long id,
            String username,
            String password,
            String accessToken,
            String idToken,
            String refreshToken,
            String fcmToken,
            String regNumber,
            String email,
            String imageUrl
    ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
        this.regNumber = regNumber;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public UserAccountEntity(String accessToken, String idToken, String refreshToken,
                             String nomorInduk,
                             String email, String imageUrl) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.regNumber = nomorInduk;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public UserAccountEntity(
            String username, String password, String regNumber, String email, String imageUrl
    ){
        this.username = username;
        this.password = password;
        this.regNumber = regNumber;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
