package com.example.fanmon_be.domain.shop.goods.entity;

import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name="usergoods")
@AllArgsConstructor
@NoArgsConstructor
public class Usergoods {

    @Id
    @Column(name = "usergoodsuuid", nullable = false)
    private UUID usergoodsuuid;

    @PrePersist
    public void generateUUID(){
        if(usergoodsuuid == null){
            usergoodsuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="useruuid")
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="goodsuuid")
    private Goods goods;
}