package com.example.fanmon_be.domain.chat.dao;

import com.example.fanmon_be.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ChatDAO extends JpaRepository<Chat, UUID> {
    @Query("SELECT c.chatuuid FROM Chat c WHERE c.artist.artistuuid = :artistuuid")
    UUID findChatuuidByArtistArtistuuid(@Param("artistuuid") UUID artistuuid);

    Chat findByArtistArtistuuid(UUID artistuuid);
}
