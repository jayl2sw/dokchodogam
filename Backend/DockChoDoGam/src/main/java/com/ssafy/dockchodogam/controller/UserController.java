package com.ssafy.dockchodogam.controller;

import com.ssafy.dockchodogam.dto.exception.common.InvalidParameterException;
import com.ssafy.dockchodogam.dto.user.*;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.jwt.TokenDto;
import com.ssafy.dockchodogam.dto.jwt.TokenRequestDto;
import com.ssafy.dockchodogam.service.user.MailService;
import com.ssafy.dockchodogam.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @GetMapping("/auth/check/nickname/{nickname}")
    @ApiOperation(value = "닉네임 중복 검사")
    public ResponseEntity<Boolean> checkNickName(@PathVariable String nickname){
        // 이미 있으면 true, 없으면 false
        return new ResponseEntity<>(userService.checkNickName(nickname), HttpStatus.OK);
    }

    @GetMapping("/auth/check/email/{email}")
    @ApiOperation(value = "이메일 중복 검사")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
        // 이미 있으면 true, 없으면 false
        return new ResponseEntity<>(userService.checkEmail(email), HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    @ApiOperation(value = "회원 가입")
    public ResponseEntity<UserResponseDto> doSignUp(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult result){

        if(result.hasErrors()){
            throw new InvalidParameterException(result);
        }
        return new ResponseEntity<>(userService.doSignUp(requestDto), HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<TokenDto> doLogin(@Valid @RequestBody LoginRequestDto requestDto, BindingResult result){
        if(result.hasErrors()){
            throw new InvalidParameterException(result);
        }
        TokenDto tokenDto = userService.doLogin(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth", tokenDto.getAccessToken());
        headers.add("Refresh", tokenDto.getRefreshToken());

        return new ResponseEntity<>(tokenDto, headers, HttpStatus.OK);
    }

    @PutMapping("/newbie")
    @ApiOperation(value = "이제 뉴비 아님")
    public ResponseEntity<String> changeNB(){
        userService.changeNB();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    
//    @PutMapping("/")
//    @ApiOperation(value = "회원 정보 수정")
//    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequestDto requestDto, BindingResult result){
//        if(result.hasErrors()){
//            throw new InvalidParameterException(result);
//        }
//        Long id = userService.getMyInfo().getUser_id();
//        userService.updateUser(id, requestDto);
//        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//    }
    @PutMapping("/password")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto dto){
        Long id = userService.getMyInfo().getUser_id();
        userService.changePW(id, passwordEncoder.encode(dto.getNewPW()));
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    
    @DeleteMapping("/")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<String> deleteUser(){
        Long id = userService.getMyInfo().getUser_id();
        userService.deleteUser(id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/auth/findpw")
    @ApiOperation(value = "비밀번호 찾기")
    public ResponseEntity<String> findPW(@RequestParam String email){
        try {
            if(!userService.checkEmail(email)){
                throw new UserNotFoundException();
            }

            String newPW = mailService.sendSimpleMessage(email);
            userService.changePW(email, passwordEncoder.encode(newPW));

            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("FAIL", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/myinfo")
    @ApiOperation(value = "내 정보 보기")
    public ResponseEntity<UserResponseDto> getMyInfo(){
        return new ResponseEntity<>(userService.getMyInfo(), HttpStatus.OK);
    }

    @PostMapping("/auth/refresh")
    @ApiOperation(value = "Access Token 재발급")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenRequestDto requestDto){
        return new ResponseEntity<>(userService.refresh(requestDto), HttpStatus.OK);
    }

    @PutMapping("/logout")
    @ApiOperation(value = "로그아웃")
    public ResponseEntity<String> logout(){
        userService.logout();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}/{page}")
    @ApiOperation(value = "회원 검색")
    public ResponseEntity<List<UserResponseDto>> search(@PathVariable String keyword, @PathVariable int page){

        return new ResponseEntity<>(userService.search(page, keyword), HttpStatus.OK);
    }

    @PutMapping("/friend/request")
    @ApiOperation(value = "친구 요청")
    public ResponseEntity<String> requestFriend(@RequestParam Long user_id){
        userService.requestFriend(user_id);
        return new ResponseEntity<>("SUCCESSS", HttpStatus.OK);
    }

    @GetMapping("/friend/request/{page}")
    @ApiOperation(value = "친구 요청 조회")
    public ResponseEntity<List<ProposeResponseDto>> showFriendRequest(@PathVariable int page){
        return new ResponseEntity<>(userService.showFriendRequest(page), HttpStatus.OK);
    }

    @PutMapping("/friend/accept")
    @ApiOperation(value = "친구 요청 수락")
    public ResponseEntity<String> acceptFriend(@RequestParam Long propose_id){
        userService.acceptFriend(propose_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/friend/refuse/{propose_id}")
    @ApiOperation(value = "친구 요청 거절")
    public ResponseEntity<String> refuseFriend(@PathVariable Long propose_id){
        userService.refuseFriend(propose_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/friend/{page}")
    @ApiOperation(value = "친구 목록")
    public ResponseEntity<List<FriendResponseDto>> getFriendList(int page){

        return new ResponseEntity<>(userService.getFriendList(page), HttpStatus.OK);
    }

    @DeleteMapping("/friend/{user_id}")
    @ApiOperation(value = "친구 삭제")
    public ResponseEntity<String> deleteFriend(@PathVariable Long user_id){
        userService.deleteFriend(user_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/friend/gift")
    @ApiOperation(value = "친구한테 돈 선물하기")
    public ResponseEntity<String> giftToFriend(@RequestParam Long user_id){
        userService.giftToFriend(user_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/friend/gift/all")
    @ApiOperation(value = "친구 모두에게 선물 보내기")
    public ResponseEntity<String> giftToAll(){
        userService.giftToAll();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    @GetMapping("/friend/gift")
    @ApiOperation(value = "내게 온 선물 확인하기")
    public ResponseEntity<List<FriendResponseDto>> getGiftList(){
        return new ResponseEntity<>(userService.getGiftList(), HttpStatus.OK);
    }

    @PutMapping("/friend/receipt")
    @ApiOperation(value = "선물 받기")
    public ResponseEntity<String> receiveGift(@RequestParam Long friend_id){
        userService.receiveGift(friend_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/friend/receipt/all")
    @ApiOperation(value = "선물 모두 받기")
    public ResponseEntity<String> receiveGiftAll(){
        userService.receiveGiftAll();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/monster")
    @ApiOperation(value = "대표 독초몬 선택")
    public ResponseEntity<String> selectRepresentMonster(@RequestParam Long monster_id){
        userService.selectRepresentMonster(monster_id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
