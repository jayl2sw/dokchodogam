package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.domain.basetime.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "user_monster")
public class UserMonster extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_monster_id")
    private Long userMonsterId;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(targetEntity = Monster.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id")
    private Monster monster;

}
