package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.dto.game.DeckRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "deck")
public class Deck {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "monster_id1")
    private Monster monster1;

    @ManyToOne
    @JoinColumn(name = "monster_id2")
    private Monster monster2;

    @ManyToOne
    @JoinColumn(name = "monster_id3")
    private Monster monster3;

    @ManyToOne
    @JoinColumn(name = "monster_id4")
    private Monster monster4;

    @ManyToOne
    @JoinColumn(name = "monster_id5")
    private Monster monster5;

    public void update(List<Monster> list){
        this.monster1 = list.get(0);
        this.monster2 = list.get(1);
        this.monster3 = list.get(2);
        this.monster4 = list.get(3);
        this.monster5 = list.get(4);
    }
}
