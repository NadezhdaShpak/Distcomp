package com.shpak.dto.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestTo {

    @Positive
    Long id;
    @NotNull(message = "editorId is required")
    @Positive
    Long editorId;
    @NotNull
    @Size(min = 2, max = 64)
    String title;
    @Size(min = 4, max = 2048)
    String content;
    LocalDateTime created;
    LocalDateTime modified;
    private List<String> labels;
}
