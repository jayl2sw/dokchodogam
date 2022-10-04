package com.ssafy.dockchodogam.domain;

import com.ssafy.dockchodogam.domain.enums.MonsterGrade;
import com.ssafy.dockchodogam.domain.enums.PlantType;
import com.ssafy.dockchodogam.dto.game.MonsterRequestDto;
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
@Table(name = "monster")
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_id")
    private Long monsterId;
    @Column(name = "name")
    private String name;
    @Column(name = "max_attack")
    private int maxAttack;
    @Column(name = "min_attack")
    private int minAttack;
    @Column(name = "type")
    private PlantType type;
    @Column(name = "images")
    private String images;
    @Column(name = "grade")
    private MonsterGrade grade;
    @Column(name = "hp")
    private int hp;

    @Column(name = "line")
    private String line;
    @OneToMany(mappedBy = "monster", fetch = FetchType.LAZY)
    private List<Plant> plant;

    @OneToMany(mappedBy = "monster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserMonster> userMonsters;

    @Column(name = "first_finder")
    private String firstFinder;

    public void setFirstFinder(String firstFinder) {
        this.firstFinder = firstFinder;
    }

    public void update(MonsterRequestDto dto){
        this.name = dto.getName();
        this.maxAttack = dto.getMaxAttack();
        this.minAttack = dto.getMinAttack();
        this.hp = dto.getHp();
        this.type = dto.getType();
        this.grade = dto.getGrade();
    }

    public void changeImage(String img){
        this.images = img;
    }
}
