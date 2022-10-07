package com.ssafy.dockchodogam.service.user;


import com.ssafy.dockchodogam.dto.jwt.TokenDto;
import com.ssafy.dockchodogam.dto.jwt.TokenRequestDto;
import com.ssafy.dockchodogam.dto.user.*;

import java.util.List;

public interface UserService {

    boolean checkEmail(String email);
    boolean checkUsername(String username);
    boolean checkNickName(String nickname);
    boolean checkPW(Long id, String nowPW);
    boolean checkSameUser(String email, String username);
    TokenDto doLogin(LoginRequestDto requestDto);
    UserResponseDto doSignUp(SignUpRequestDto requestDto);
    void changeNB();
    //UserResponseDto getUserInfo(Long id);
    //void updateUser(Long id, UpdateUserRequestDto requestDto);
    void deleteUser(Long id);
    void changePW(String email, String newPW);
    void changePW(Long id, String newPW);
    void setNickname(String nickname);
    UserResponseDto getMyInfo();

    UserResponseDto getUserInfo(Long userId);
    TokenDto refresh(TokenRequestDto requestDto);
    void logout();
    UserResponseDto search(String keyword);
    void requestFriend(Long id);
    void acceptFriend(Long id);
    void refuseFriend(Long id);
    List<FriendResponseDto> getFriendList();

    List<UserResponseDto> getFriendInfoList();
    void deleteFriend(Long id);
    List<ProposeResponseDto> showFriendRequest();
    int getFriendCount(Long id);
    void selectRepresentMonster(Long id);
    void giftToFriend(Long id);
    void giftToAll();
    List<FriendResponseDto> getGiftList();
    void receiveGift(Long id);
    void receiveGiftAll();
    void changeRankPoint(int point);
}
