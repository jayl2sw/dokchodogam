package com.ssafy.dockchodogam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long friendId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id1")
    private User userA;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id2")
    private User userB;

    @Column(name = "gift_date")
    private String giftDate;

    @Column(name = "gift_exist")
    private Boolean giftExist;

    public void setGiftDate(){
        this.giftDate = LocalDate.now().toString();
    }
    public void setGiftExist(boolean flag){this.giftExist = flag;}
}
