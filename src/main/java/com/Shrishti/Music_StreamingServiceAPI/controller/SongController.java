package com.Shrishti.Music_StreamingServiceAPI.controller;

import com.Shrishti.Music_StreamingServiceAPI.model.Song;
import com.Shrishti.Music_StreamingServiceAPI.service.SongService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/songDetails")
public class SongController {

    @Autowired
    SongService songService;

    //Add Song List
    @PostMapping(value = "/song/adminMail/{adminEmail}")
    private String addSong(@RequestBody List<Song> song, @PathVariable String adminEmail){
        return songService.addSong(song, adminEmail);
    }

    //Get All Songs
    @GetMapping(value = "/adminEmail/{adminEmail}")
    private List<Song> getAllSongs(@PathVariable String adminEmail){
        return songService.getAllSongs(adminEmail);
    }

    //Get Song By Id
    @GetMapping(value = "/songId/{songId}/adminEmail/{adminEmail}")
    private Song getSongById(@PathVariable Integer songId, @PathVariable String adminEmail){
        return songService.getSongById(songId, adminEmail);
    }

    //Update Song By Id
    @PutMapping(value = "/update/songId/{songId}/adminMail/{adminEmail}")
    private String updateSong(@RequestBody Song song, @PathVariable Integer songId, @PathVariable String adminEmail){
        return songService.updateSong(song,songId,adminEmail);
    }

    //Delete Song By Id
    @DeleteMapping(value = "/delete/songI/{songId}/adminEmail/{adminEmail}")
    private String deleteSong(@PathVariable Integer songId, @PathVariable String adminEmail){
        return songService.deleteSong(songId,adminEmail);
    }
}
