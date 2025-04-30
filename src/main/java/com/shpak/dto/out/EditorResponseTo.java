package com.shpak.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Long> issueIds;
}
