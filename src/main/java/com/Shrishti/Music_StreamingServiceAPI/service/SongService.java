package com.Shrishti.Music_StreamingServiceAPI.service;

import com.Shrishti.Music_StreamingServiceAPI.model.Admin;
import com.Shrishti.Music_StreamingServiceAPI.model.Song;
import com.Shrishti.Music_StreamingServiceAPI.repository.IAdminDao;
import com.Shrishti.Music_StreamingServiceAPI.repository.ISongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    ISongDao songDao;

    @Autowired
    IAdminDao adminDao;

    public String addSong(List<Song> song, String adminEmail) {

        //Check that admin is valid or not
        Admin admin = adminDao.findByAdminEmail(adminEmail);

        if(admin == null){
            throw new IllegalStateException("This Admin is not Registered or You are Not and Admin...User Cant Add Songs!!");
        }
        else{
            songDao.saveAll(song);
            return "Song List is Added!!! Thank You Admin -  " + admin.getAdminUserName();
        }
    }

    public List<Song> getAllSongs(String adminEmail) {

        //Check that admin is valid or not
        Admin admin = adminDao.findByAdminEmail(adminEmail);

        if(admin == null){
            throw new IllegalStateException("This Admin is not Registered or You are Not and Admin");
        }
        else{
            return songDao.findAll();
        }
    }

    public Song getSongById(Integer songId, String adminEmail) {
        //Check that admin is valid or not
        Admin admin = adminDao.findByAdminEmail(adminEmail);

        if(admin == null){
            throw new IllegalStateException("This Admin is not Registered or You are Not and Admin");
        }
        else{
            return songDao.findById(songId).get();
        }
    }

    public String updateSong(Song song, Integer songId, String adminEmail) {
        //Check that admin is valid or not
        Admin admin = adminDao.findByAdminEmail(adminEmail);

        if(admin == null){
            throw new IllegalStateException("This Admin is not Registered or You are Not and Admin");
        }
        Song oldSong = songDao.findById(songId).get();
        if(oldSong != null){
            oldSong.setSongName(song.getSongName());
            oldSong.setSongArtist(song.getSongArtist());
            oldSong.setSongDuration(song.getSongDuration());
            songDao.save(oldSong);
            return oldSong.getSongName() + "   is Updated!!!";
        }
        return "Song not Updated due to this song is not Exist!!!";
    }


    public String deleteSong(Integer songId, String adminEmail) {
        //Check that admin is valid or not
        Admin admin = adminDao.findByAdminEmail(adminEmail);

        if(admin == null){
            throw new IllegalStateException("This Admin is not Registered or You are Not and Admin");
        }

        Song deletableSong = songDao.findById(songId).get();
        if(deletableSong != null){
            songDao.delete(deletableSong);
            return deletableSong.getSongName() + "  is Deleted Successfully!!!";
        }
        return "Song Id Is Not Exist!! Thank You...";
    }
}
