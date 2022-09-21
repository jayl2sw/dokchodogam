package com.ssafy.dockchodogam.service.user;


import com.ssafy.dockchodogam.dto.jwt.TokenDto;
import com.ssafy.dockchodogam.dto.jwt.TokenRequestDto;
import com.ssafy.dockchodogam.dto.user.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    boolean checkEmail(String email);
    boolean checkNickName(String nickname);
    TokenDto doLogin(LoginRequestDto requestDto);
    UserResponseDto doSignUp(SignUpRequestDto requestDto);
    void changeNB();
    //UserResponseDto getUserInfo(Long id);
    //void updateUser(Long id, UpdateUserRequestDto requestDto);
    void deleteUser(Long id);
    void changePW(String email, String newPW);
    void changePW(Long id, String newPW);
    UserResponseDto getMyInfo();
    TokenDto refresh(TokenRequestDto requestDto);
    void logout();
    List<UserResponseDto> search(int page, String keyword);
    void requestFriend(Long id);
    void acceptFriend(Long id);
    void refuseFriend(Long id);
    List<FriendResponseDto> getFriendList(int page);

    List<UserResponseDto> getFriendInfoList(Pageable pageable);
    void deleteFriend(Long id);
    List<ProposeResponseDto> showFriendRequest(int page);
    int getFriendCount(Long id);
    void selectRepresentMonster(Long id);
    void giftToFriend(Long id);
    void giftToAll();
    List<FriendResponseDto> getGiftList();
    void receiveGift(Long id);
    void receiveGiftAll();
}
