package com.example.fanmon_be.domain.shop.buying.entity;

import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name="cardinfo")
@AllArgsConstructor
@NoArgsConstructor
public class Cardinfo {

    @Id
    @Column(name = "cardinfouuid", nullable = false)
    private UUID cardinfouuid;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="ordersuuid", nullable = false)
    private Orders orders;

    private String approval;
    private String brand;
    private String number;
    private String currency;
    private String provider;
    private String type;
}
