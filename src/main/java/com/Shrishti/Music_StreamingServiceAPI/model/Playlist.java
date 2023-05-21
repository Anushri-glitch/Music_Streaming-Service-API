package com.Shrishti.Music_StreamingServiceAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer playlistId;
    private String playlistName;
    private Integer totalSongs;

    @ManyToMany
    @JoinColumn(name = "FK_song_Name")
    private List<Song> songList = new ArrayList<>();
}
