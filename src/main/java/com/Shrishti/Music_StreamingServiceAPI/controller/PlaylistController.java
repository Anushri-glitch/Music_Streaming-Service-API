package com.Shrishti.Music_StreamingServiceAPI.controller;

import com.Shrishti.Music_StreamingServiceAPI.model.Playlist;
import com.Shrishti.Music_StreamingServiceAPI.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    //Create PlayList
    @PostMapping(value = "/userMail/{userEmail}")
    private String addPlaylist(@RequestBody String playlist, @PathVariable String userEmail){
        Playlist obj = playlistService.EditPlaylist(playlist);
        String response = playlistService.addPlaylist(obj,userEmail);
        return "  PlayList With Name " + response + "  is Saved!!";
    }

    //Get Playlist
    @GetMapping(value = "/RunPlaylist")
    private Playlist getPlaylist(@RequestParam Integer playlistId, @RequestParam String userMail){
        return playlistService.getPlaylist(playlistId, userMail);
    }

    //Update PlayList
    @PutMapping(value = "/updatePlaylist")
    private String updatePlaylist(@RequestBody String newList, @RequestParam Integer playlistId, @RequestParam String userEmail){
        Playlist obj = playlistService.UpdateThisPlaylist(newList,playlistId);
        String response = playlistService.UpdatePlaylist(obj,userEmail);
        return "  PlayList With Name " + response + "  is Updated!!";
    }

    //Delete Playlist
    @DeleteMapping(value = "/deletePlaylist")
    private String deletePlaylist(@RequestParam Integer playlistId, @RequestParam String userEmail){
        return playlistService.deletePlaylist(playlistId, userEmail);
    }
}
