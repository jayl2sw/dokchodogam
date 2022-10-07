package com.ssafy.dockchodogam.dto.user;

import com.ssafy.dockchodogam.domain.Friend;
import com.ssafy.dockchodogam.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FriendResponseDto", description = "친구 정보 응답 Dto")
public class FriendResponseDto {
    private Long friend_id;
    private Long user_id;
    private String nickname;
    private Boolean gift_today;
    private Boolean gift_exist;
    private Integer rank_point;
    private Long profile_img;

    public static FriendResponseDto from(Friend friend, User user){
        boolean flag = false;
        if(friend.getGiftDate() != null && friend.getGiftDate().equals(LocalDate.now().toString())){
            flag = true;
        }

        return FriendResponseDto.builder()
                .friend_id(friend.getFriendId())
                .user_id(user.getUserId())
                .nickname(user.getNickname())
                .rank_point(user.getRankPoint())
                .gift_today(flag)
                .gift_exist(friend.getGiftExist())
                .profile_img(user.getRepresentMonster().getMonsterId())
                .build();
    }
}
