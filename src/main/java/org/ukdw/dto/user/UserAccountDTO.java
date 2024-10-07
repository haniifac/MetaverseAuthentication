package org.ukdw.dto.user;

import lombok.Data;

@Data
public class UserAccountDTO {

    String idUser;

    String nama;

    String email;

    String role;

    String refreshToken;

    String fcmToken;
}
