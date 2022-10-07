package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.domain.basetime.BaseTime;
import com.ssafy.dockchodogam.domain.enums.Role;
import com.ssafy.dockchodogam.dto.user.UpdateUserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "rank_point")
    private Integer rankPoint;
    @Column(name = "money")
    private Integer money;
    @Column(name = "newbie")
    private Boolean newbie;
    @Column(name = "starter")
    private Boolean starter;
    @Column(name = "role")
    private Role role;
    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserMonster> userMonsters;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Deck deck;
    @ManyToOne
    @JoinColumn(name = "monster_id")
    private Monster representMonster;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Purchases> purchases;
    @OneToMany(mappedBy = "attacker", cascade = CascadeType.ALL)
    private List<Battle> attack;
    @OneToMany(mappedBy = "defender", cascade = CascadeType.ALL)
    private List<Battle> defend;
    @OneToMany(mappedBy = "userA", cascade = CascadeType.ALL)
    private List<Friend> friendA;
    @OneToMany(mappedBy = "userB", cascade = CascadeType.ALL)
    private List<Friend> friendB;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Propose> proposeA;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Propose> proposeB;

    public void update(UpdateUserRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
    }

    public void changeMoney(int money){this.money = money;}

    public void saveToken(String token) {
        this.token = token;
    }

    public void changeRankPoint(int point){
        this.rankPoint += point;

        if(this.rankPoint < 0){
            this.rankPoint = 0;
        }
    }

    public void changePW(String password){this.password = password;}
    public void setNickname(String nickname){this.nickname = nickname;}
    public void setRepresentMonster(Monster monster){this.representMonster = monster;}
    public void changeNB(){this.newbie = false;}
    public void buyStarter(){this.starter = false;}

    @PrePersist
    public void prePersist(){
        this.money = this.money == null ? 0 : this.money;
        this.rankPoint = this.rankPoint == null ? 0 : this.rankPoint;
        this.newbie = this.newbie == null ? true : this.newbie;
        this.starter = this.starter == null ? true : this.starter;
    }
}
