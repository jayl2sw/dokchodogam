package com.ssafy.dockchodogam.dto.gg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GGRequestDto {
    private int offset;
    private int limit;
}
