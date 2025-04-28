package com.shpak.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorResponseTo {
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
}
