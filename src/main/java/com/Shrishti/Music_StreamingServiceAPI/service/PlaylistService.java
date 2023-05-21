package com.Shrishti.Music_StreamingServiceAPI.service;

import com.Shrishti.Music_StreamingServiceAPI.model.Playlist;
import com.Shrishti.Music_StreamingServiceAPI.model.Song;
import com.Shrishti.Music_StreamingServiceAPI.model.User;
import com.Shrishti.Music_StreamingServiceAPI.repository.IPlaylistDao;
import com.Shrishti.Music_StreamingServiceAPI.repository.ISongDao;
import com.Shrishti.Music_StreamingServiceAPI.repository.IUserDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    IPlaylistDao playlistDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ISongDao songDao;

    public String addPlaylist(Playlist playlist, String userEmail) {

        User user = userDao.findFirstByUserEmail(userEmail);

        if(user == null){
            throw new IllegalStateException("Please registered YourSelf!!!...");
        }

        playlistDao.save(playlist);
        return playlist.getPlaylistName();
    }

    public Playlist EditPlaylist(String playlist) {
        Integer songCount = 0;
        JSONObject newObject = new JSONObject(playlist);

        Playlist newList = new Playlist();
        newList.setPlaylistName(newObject.getString("playlistName"));
        String newSongList = newObject.getString("songList");
        String[] songListArray = newSongList.split(",");
        List<Song> songArray = new ArrayList<>();

        for(String song : songListArray){
            if(songDao.existsById(Integer.valueOf(song))){
                songCount++;
                Song gana = songDao.findById(Integer.valueOf(song)).get();
                songArray.add(gana);
            }
        }

        newList.setTotalSongs(songCount);
        newList.setSongList(songArray);
        return newList;
    }

    public Playlist getPlaylist(Integer playlistId, String userMail) {
        //Check user Exist or Not
        User user = userDao.findFirstByUserEmail(userMail);

        if(user == null){
            throw new IllegalStateException("Please registered YourSelf!!!...");
        }
        else{
            Playlist newList = playlistDao.findById(playlistId).get();
            if(newList == null){
                throw new IllegalStateException("PlayList ot Exist!!");
            }
            return newList;
        }
    }

    //Set PlayList By Function - For Update PlayList
    public Playlist UpdateThisPlaylist(String playlist, Integer playlistId) {
        Playlist gana = playlistDao.findById(playlistId).get();
        if(gana != null){
            Integer songCount = 0;
            JSONObject newObject = new JSONObject(playlist);

            Playlist newList = new Playlist();
            newList.setPlaylistName(newObject.getString("playlistName"));
            String newSongList = newObject.getString("songList");
            String[] songListArray = newSongList.split(",");
            List<Song> songArray = new ArrayList<>();

            for(String song : songListArray){
                if(songDao.existsById(Integer.valueOf(song))){
                    songCount++;
                    Song newGana = songDao.findById(Integer.valueOf(song)).get();
                    songArray.add(newGana);
                }
            }

            newList.setTotalSongs(songCount);
            newList.setSongList(songArray);
            return newList;
        }
        return null;
    }


    public String UpdatePlaylist(Playlist obj, String userEmail) {
        //Check user Exist or Not
        User user = userDao.findFirstByUserEmail(userEmail);

        if(user == null){
            throw new IllegalStateException("Please registered YourSelf!!!...");
        }
        playlistDao.save(obj);
        return obj.getPlaylistName();
    }

    public String deletePlaylist(Integer playlistId, String userEmail) {

        User user = userDao.findFirstByUserEmail(userEmail);
        if(user == null){
            throw new IllegalStateException("Please registered Yourself!!");
        }
        else{
            Playlist getList = playlistDao.findById(playlistId).get();
            if(getList != null){
                playlistDao.deleteById(playlistId);
                return  getList.getPlaylistName() + "  is Deleted Successfully!!!";
            }
            return "This PlayList is not Exist!!!";
        }
    }
}
