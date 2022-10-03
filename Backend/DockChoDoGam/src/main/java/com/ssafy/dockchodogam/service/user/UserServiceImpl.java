package com.ssafy.dockchodogam.service.user;

import com.ssafy.dockchodogam.domain.*;
import com.ssafy.dockchodogam.domain.enums.Role;
import com.ssafy.dockchodogam.dto.exception.game.MonsterNotFoundException;
import com.ssafy.dockchodogam.dto.exception.user.*;
import com.ssafy.dockchodogam.dto.user.*;
import com.ssafy.dockchodogam.repository.*;
import com.ssafy.dockchodogam.dto.jwt.TokenDto;
import com.ssafy.dockchodogam.dto.jwt.TokenRequestDto;
import com.ssafy.dockchodogam.jwt.TokenProvider;
import com.ssafy.dockchodogam.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendRepository friendRepository;
    private final ProposeRepository proposeRepository;
    private final MonsterRepository monsterRepository;
    private final UserMonsterRepository userMonsterRepository;
    private final DeckRepository deckRepository;

    @Override
    public boolean checkEmail(String email) {
        // 이미 있으면 true, 없으면 false
        Optional<User> entity = userRepository.findByEmail(email);

        return entity.isPresent();
    }

    @Override
    public boolean checkUsername(String username){
        Optional<User> entity = userRepository.findByUsername(username);

        return entity.isPresent();
    }

    @Override
    public boolean checkNickName(String nickname) {
        // 이미 있으면 true, 없으면 false
        Optional<User> entity = userRepository.findByNickname(nickname);

        return entity.isPresent();
    }

    @Override
    public boolean checkPW(Long id, String nowPW){
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new).getPassword() == nowPW;
    }

    @Override
    public boolean checkSameUser(String email, String username){
        Long emailId = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new).getUserId();
        Long nameId = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new).getUserId();

        return emailId == nameId;
    }

    @Override
    public TokenDto doLogin(LoginRequestDto requestDto) {
        // Login id/pw로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());

        // 검증 과정
        // CustomUserDetailsService의 loadByUserName 실행
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // Refresh Token 저장
        Optional<User> entity = userRepository.findByUsername(authentication.getName());

        if(entity.isPresent()){
            entity.get().saveToken(tokenDto.getRefreshToken());
            userRepository.save(entity.get());
        }

        // 토큰 발급
        return tokenDto;
    }

    @Override
    public UserResponseDto doSignUp(SignUpRequestDto requestDto) {

        if(userRepository.findByUsername(requestDto.getUsername()).orElse(null)!=null){
            throw new DuplicateUsernameException();
        }

        if(userRepository.findByEmail(requestDto.getEmail()).orElse(null)!=null){
            throw new DuplicateEmailException();
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .nickname("")
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.ROLE_USER)
                // 대표 독초몬은 개나리몬이 디폴트
                .representMonster(monsterRepository.findById(Long.parseLong("8")).orElseThrow(MonsterNotFoundException::new))
                .build();

        Deck deck = Deck.builder()
                .user(user)
                .monster1(monsterRepository.findById(Long.parseLong("8")).orElseThrow(MonsterNotFoundException::new))
                .monster2(monsterRepository.findById(Long.parseLong("18")).orElseThrow(MonsterNotFoundException::new))
                .monster3(monsterRepository.findById(Long.parseLong("46")).orElseThrow(MonsterNotFoundException::new))
                .monster4(monsterRepository.findById(Long.parseLong("80")).orElseThrow(MonsterNotFoundException::new))
                .monster5(monsterRepository.findById(Long.parseLong("89")).orElseThrow(MonsterNotFoundException::new))
                .build();

        deckRepository.save(deck);

        // 기본 독초몬 지급하기
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monsterRepository.findById(Long.parseLong("8")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monsterRepository.findById(Long.parseLong("18")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monsterRepository.findById(Long.parseLong("46")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monsterRepository.findById(Long.parseLong("80")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(user).monster(monsterRepository.findById(Long.parseLong("89")).orElseThrow(MonsterNotFoundException::new)).build());

        return UserResponseDto.from(user);
    }

    @Override
    public void changeNB(){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        me.changeNB();
        userRepository.save(me);
    }

    @Override
    public UserResponseDto getMyInfo(){
        return UserResponseDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new));
    }


    @Override
    public UserResponseDto getUserInfo(Long userId) {
        return UserResponseDto.from(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }

//  현재 사용처 없음
//    @Override
//    public UserResponseDto getUserInfo(Long id){
//        return UserResponseDto.from(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
//    }

//    @Override
//    public void updateUser(Long id, UpdateUserRequestDto requestDto){
//        Optional<User> entity = userRepository.findById(id);
//
//        if(entity.isPresent()){
//            if(requestDto.getPassword().equals("")){
//                entity.get().update(requestDto);
//                userRepository.save(entity.get());
//                return;
//            }
//
//            entity.get().update(requestDto, passwordEncoder);
//            userRepository.save(entity.get());
//            return;
//        }
//
//        throw new UserNotFoundException();
//    }

    @Override
    public void deleteUser(Long id){
        Optional<User> entity = userRepository.findById(id);

        if(entity.isPresent()){
            userRepository.delete(entity.get());
            return;
        }

        throw new UserNotFoundException();
    }

    @Override
    public void changePW(String email, String newPW) {
        Optional<User> entity = userRepository.findByEmail(email);

        if(entity.isPresent()){
            entity.get().changePW(newPW);
            return;
        }

        throw new UserNotFoundException();
    }

    @Override
    public void changePW(Long id, String newPW){
        Optional<User> entity = userRepository.findById(id);

        if(entity.isPresent()){
            entity.get().changePW(newPW);
            return;
        }

        throw new UserNotFoundException();
    }

    @Override
    public void setNickname(String nickname){
        if(checkNickName(nickname)) throw new DuplicateNicknameException();

        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        me.setNickname(nickname);
        userRepository.save(me);
    }

    @Override
    public TokenDto refresh(TokenRequestDto requestDto){
        // Refresh Token 검증
        if(!tokenProvider.validateToken(requestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        // Access Token에서 Id(username) 가져오기
        Authentication authentication = tokenProvider.getAuthentication(requestDto.getAccessToken());

        // 가져온 ID로 Refresh Token 가져오기
        User entity = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new RuntimeException("로그아웃된 사용자입니다."));

        String refreshToken = entity.getToken();

        // 일치 검사
        if(!refreshToken.equals(requestDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // DB 정보 업데이트
        entity.saveToken(tokenDto.getRefreshToken());

        // 토큰 발급
        return tokenDto;
    }

    @Override
    public void logout(){
        Optional<User> entity = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername);

        if(entity.isPresent()){
            entity.get().saveToken("");
            userRepository.save(entity.get());
            return;
        }

        throw new RuntimeException("로그아웃에 실패했습니다.");
    }

    @Override
    public UserResponseDto search(String keyword) {
        User user = userRepository.findByNickname(keyword).orElseThrow(UserNotFoundException::new);

        return UserResponseDto.from(user);
    }

    @Override
    public void requestFriend(Long id) {
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);

        User other = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Optional<Propose> propose = proposeRepository.findByTwoId(me.getUserId(), other.getUserId());
        if(propose.isPresent()){
            throw new DuplicateProposeException();
        }

        Optional<Friend> friend = friendRepository.findByTwoId(me.getUserId(), other.getUserId());
        if(friend.isPresent()){
            throw new DuplicateFriendException();
        }

        proposeRepository.save(Propose.builder().sender(me).receiver(other).build());
    }

    @Override
    public List<ProposeResponseDto> showFriendRequest(){
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();
        return proposeRepository.findAllByReceiver(myId).stream()
                .map(propose -> ProposeResponseDto.from(propose)).collect(Collectors.toList());
    }

    @Override
    public void acceptFriend(Long id) {
        Propose propose = proposeRepository.findById(id).orElseThrow(RuntimeException::new);
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        User other = propose.getSender();

//        if(getFriendCount(me.getUserId()) >= 10 || getFriendCount(other.getUserId()) >= 10){
//            proposeRepository.delete(propose);
//            throw new TooManyFriendsException();
//        }
        Optional<Friend> friend = friendRepository.findByTwoId(me.getUserId(), other.getUserId());
        if(friend.isPresent()){
            throw new DuplicateFriendException();
        }

        friendRepository.save(Friend.builder().userA(me).userB(other).giftExist(false).build());
        friendRepository.save(Friend.builder().userA(other).userB(me).giftExist(false).build());
        proposeRepository.delete(propose);
    }

    @Override
    public void refuseFriend(Long id) {
        Propose propose = proposeRepository.findById(id).orElseThrow(RuntimeException::new);
        proposeRepository.delete(propose);
    }

    @Override
    public List<FriendResponseDto> getFriendList() {
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);

        return friendRepository.findByUserA(me).stream()
                .map(friend->FriendResponseDto.from(friend, friend.getUserB()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getFriendInfoList() {
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        return friendRepository.findByUserA(me)
                .stream().map(s -> UserResponseDto.from(s.getUserB())).collect(Collectors.toList());
    }

    @Override
    public void deleteFriend(Long id) {
        Long myId = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new).getUserId();

        Friend friend1 = friendRepository.findByTwoId(id, myId).orElseThrow(UserNotFoundException::new);
        Friend friend2 = friendRepository.findByTwoId(myId, id).orElseThrow(UserNotFoundException::new);
        friendRepository.delete(friend1);
        friendRepository.delete(friend2);
    }

    @Override
    public int getFriendCount(Long id){
        return friendRepository.countById(id);
    }

    @Override
    public void giftToFriend(Long id){

        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        User other = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Friend friend = friendRepository.findByTwoId(me.getUserId(), other.getUserId()).orElseThrow(UserNotFoundException::new);
        if(friend.getGiftDate()!=null && friend.getGiftDate().equals(LocalDate.now().toString())){
            throw new RuntimeException("잘못된 값");
        }

        if(friend.getGiftExist()){
            throw new RuntimeException("이미 선물함");
        }

        friend.setGiftExist(true);
        friend.setGiftDate();
        friendRepository.save(friend);
    }

    @Override
    public void giftToAll(){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        List<Friend> friendList = friendRepository.findByUserAAndGiftExist(me, false);
        for(Friend f: friendList){
            if(f.getGiftDate() != null && f.getGiftDate().equals(LocalDate.now().toString())){
                continue;
            }

            f.setGiftExist(true);
            f.setGiftDate();
            friendRepository.save(f);
        }
    }

    @Override
    public List<FriendResponseDto> getGiftList(){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        return friendRepository.findByUserBAndGiftExist(me, true).stream()
                .map(friend -> FriendResponseDto.from(friend, friend.getUserA()))
                .collect(Collectors.toList());
    }

    @Override
    public void receiveGift(Long id){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        Friend friend = friendRepository.findById(id).orElseThrow(UserNotFoundException::new);
        friend.setGiftExist(false);
        me.changeMoney(me.getMoney()+1);
        userRepository.save(me);
        friendRepository.save(friend);
    }

    @Override
    public void receiveGiftAll(){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        List<Friend> friendList = friendRepository.findByUserBAndGiftExist(me, true);

        if(friendList == null){
            throw new RuntimeException("받은 선물이 없습니다.");
        }

        int count = friendList.size();

        for(Friend f: friendList){
            f.setGiftExist(false);
            friendRepository.save(f);
        }

        me.changeMoney(me.getMoney()+count);
        userRepository.save(me);
    }

    @Override
    public void selectRepresentMonster(Long id){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        Monster monster =  monsterRepository.findById(id).orElseThrow(MonsterNotFoundException::new);

        me.setRepresentMonster(monster);
        userRepository.save(me);
    }

    @Override
    public void changeRankPoint(int point){
        User me = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElseThrow(UserNotFoundException::new);
        me.changeRankPoint(point);

        userRepository.save(me);
    }
}
