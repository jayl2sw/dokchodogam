package com.ssafy.dockchodogam.dto.user;

import com.ssafy.dockchodogam.domain.Propose;
import com.ssafy.dockchodogam.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProposeResponseDto", description = "친구 요청 Dto")
public class ProposeResponseDto {
    private Long propose_id;
    private Long sender_id;
    private String sender_nickname;
    private String sender_username;

    public static ProposeResponseDto from(Propose entity){
        User sender = entity.getSender();

        return ProposeResponseDto.builder()
                .propose_id(entity.getProposeId())
                .sender_id(sender.getUserId())
                .sender_nickname(sender.getNickname())
                .sender_username(sender.getUsername())
                .build();
    }
}
