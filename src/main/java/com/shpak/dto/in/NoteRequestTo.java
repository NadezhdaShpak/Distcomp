package com.shpak.dto.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestTo {
    @Positive
    Long id;
    @NotNull(message = "editorId is required")
    @Positive
    Long issueId;
    @Size(min = 2, max = 2048)
    String content;
}
