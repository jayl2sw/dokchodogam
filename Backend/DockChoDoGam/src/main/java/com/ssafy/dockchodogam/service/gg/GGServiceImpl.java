package com.ssafy.dockchodogam.service.gg;

import com.ssafy.dockchodogam.domain.User;
import com.ssafy.dockchodogam.dto.exception.user.UserNotFoundException;
import com.ssafy.dockchodogam.dto.battle.BattleDto;
import com.ssafy.dockchodogam.repository.BattleLogRepository;
import com.ssafy.dockchodogam.repository.BattleRepository;
import com.ssafy.dockchodogam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GGServiceImpl implements GGService{

    private final UserRepository userRepository;
    private final BattleRepository battleRepository;
    private final BattleLogRepository battleLogRepository;

    @Override
    public List<BattleDto> findBattlesByNickname(String nickname, int offset, int limit) {
        User user = userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
        List<BattleDto> battles = battleRepository.findBattlesByUser(user, offset, limit).stream()
                .map(b -> new BattleDto().from(b)).collect(Collectors.toList());

        return battles;

    }
}
