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
@Table(name = "battle")
public class Battle extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name="battle_id")
    private long battle_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id1")
    private User attacker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id2")
    private User defender;

    private boolean success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id0")
    private Monster monster0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id1")
    private Monster monster1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id2")
    private Monster monster2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id3")
    private Monster monster3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id4")
    private Monster monster4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id5")
    private Monster monster5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id6")
    private Monster monster6;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id7")
    private Monster monster7;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id8")
    private Monster monster8;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id9")
    private Monster monster9;

    public void successBattle() { this.success = true; }

    public Battle(User attacker, User defender, boolean success, Monster monster0, Monster monster1, Monster monster2, Monster monster3, Monster monster4, Monster monster5, Monster monster6, Monster monster7, Monster monster8, Monster monster9) {
        this.attacker = attacker;
        this.defender = defender;
        this.success = success;
        this.monster0 = monster0;
        this.monster1 = monster1;
        this.monster2 = monster2;
        this.monster3 = monster3;
        this.monster4 = monster4;
        this.monster5 = monster5;
        this.monster6 = monster6;
        this.monster7 = monster7;
        this.monster8 = monster8;
        this.monster9 = monster9;
    }
}
