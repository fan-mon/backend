package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.SubscribeDAO;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscribeService {
    @Autowired
    private SubscribeDAO dao;

    public Subscribe getSubscription(UUID useruuid, UUID artistuuid){
        Subscribe subscribe = dao.findByUserUseruuidAndChatArtistArtistuuid(useruuid, artistuuid);
        if (subscribe != null){
            return subscribe;
        }
        return null;
    }
    public Subscribe getSubscription2(UUID useruuid, UUID chatuuid){
        Subscribe subscribe = dao.findByUserUseruuidAndChatChatuuid(useruuid, chatuuid);
        if (subscribe != null){
            return subscribe;
        }
        return null;
    }
}
