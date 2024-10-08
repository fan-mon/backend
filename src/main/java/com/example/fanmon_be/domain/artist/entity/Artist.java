package com.example.fanmon_be.domain.artist.entity;

import com.example.fanmon_be.domain.management.entity.Management;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "artist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @Column(name = "artistuuid", nullable = false)
    private UUID artistuuid;

    @PrePersist
    public void generateUUID() {
        if (artistuuid == null) {
            artistuuid = UUID.randomUUID();
        }
    }

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="managementuuid", insertable=true, updatable=true)
    private Management management;

    private LocalDate debut;
    private String email;
    private String password;
    private LocalDate birth;
    private String fname;

    @Transient
    @JsonIgnore
    private MultipartFile uploadfile;
}
