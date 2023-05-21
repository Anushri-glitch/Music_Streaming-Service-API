package com.Shrishti.Music_StreamingServiceAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer songId;
    private String songName;
    private String songArtist;
    private String songDuration;
}
