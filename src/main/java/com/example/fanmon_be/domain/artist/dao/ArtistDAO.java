package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtistDAO extends JpaRepository<Artist, UUID> {

    List<Artist> findArtistsByManagementManagementuuid(UUID managementuuid);

    Artist findByArtistuuid(UUID artistuuid);

    Optional<Artist> findByEmail(String email);
}
