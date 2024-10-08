package com.example.fanmon_be.domain.shop.goods.dao;

import com.example.fanmon_be.domain.shop.goods.entity.Usergoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsergoodsDAO extends JpaRepository<Usergoods, UUID> {
}
