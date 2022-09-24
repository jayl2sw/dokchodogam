package com.ssafy.dockchodogam.service.oauth2;

import com.ssafy.dockchodogam.domain.Deck;
import com.ssafy.dockchodogam.domain.Monster;
import com.ssafy.dockchodogam.domain.UserMonster;
import com.ssafy.dockchodogam.domain.enums.Role;
import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.dto.exception.game.MonsterNotFoundException;
import com.ssafy.dockchodogam.repository.DeckRepository;
import com.ssafy.dockchodogam.repository.MonsterRepository;
import com.ssafy.dockchodogam.repository.UserMonsterRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import com.ssafy.dockchodogam.dto.jwt.TokenDto;
import com.ssafy.dockchodogam.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final MonsterRepository monsterRepository;
    private final UserMonsterRepository userMonsterRepository;
    private final DeckRepository deckRepository;
    private final PasswordMaker passwordMaker;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if(attributes.get("id")!=null){
            attributes = (Map<String, Object>) attributes.get("id");
        }

        String email = (String) attributes.get("email");
        String nickname = (String) attributes.get("profile");

        // db 확인
        // 새로운 유저 -> 회원가입
        User entity = userRepository.findByEmail(email)
                .orElse(User.builder()
                        .username(passwordMaker.make())
                        .password(passwordMaker.make())
                        .email(email)
                        .nickname(nickname)
                        .role(Role.ROLE_USER)
                        .representMonster(monsterRepository.findById(Long.parseLong("8")).orElseThrow(MonsterNotFoundException::new))
                        .build());

        Monster zero = monsterRepository.findById(Long.parseLong("0")).orElseThrow(MonsterNotFoundException::new);

        Deck deck = Deck.builder()
                .user(entity)
                .monster1(zero).monster2(zero).monster3(zero).monster4(zero).monster5(zero).build();

        deckRepository.save(deck);

        // 기본 독초몬 지급하기
        userMonsterRepository.save(UserMonster.builder().user(entity).monster(monsterRepository.findById(Long.parseLong("8")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(entity).monster(monsterRepository.findById(Long.parseLong("46")).orElseThrow(MonsterNotFoundException::new)).build());
        userMonsterRepository.save(UserMonster.builder().user(entity).monster(monsterRepository.findById(Long.parseLong("80")).orElseThrow(MonsterNotFoundException::new)).build());

        // 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(entity.getEmail(), entity.getRole().toString());
        entity.saveToken(tokenDto.getRefreshToken());
        userRepository.save(entity);

        // 리다이렉트
        String target = "http://j7e201.p.ssafy.io/oauth?Auth=" + tokenDto.getAccessToken() + "&Refresh=" + tokenDto.getRefreshToken();
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, target);
    }
}
