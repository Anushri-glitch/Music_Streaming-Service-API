# Music_Streaming-Service-API
This Application Based On MySQL Database

##### :purple_square: Its an API Based Web Application
## :one: Frameworks and Languages Used -
    1. SpringBoot
    2. JAVA
    3. Postman
    4. MySQL
    
## :two: Dependency Used
    1. Spring Web
    2. Spring Boot Dev Tools
    3. Lombok
    4. Spring Data JPA
    5. H2 Database
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
## :three: Dataflow (Functions Used In)
### :purple_square: 1. Model - Model is used to Iniitialize the required attributes and create the accessable constructors and methods
#### :o: user.java
```java
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPhone;
}
```

#### :o: Admin.java
```java
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer adminId;
    private String adminUserName;

    @Pattern(regexp = "^[a-z0-9]{3,}@[admin]{3,5}[.]{1}[com]{1,3}$")
    private String adminEmail;
    private String adminPassword;
    private String adminPhone;
}
```

#### :o: AuthTokenUser.java
```java
public class AuthTokenUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;

    private String token;
    private LocalDate tokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "fk_user_Id")
    private User user;
}
```

#### :o: AuthTokenAdmin.java
```java
public class AuthTokenAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminTokenId;

    private String adminToken;
    private LocalDate adminTokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "fk_admin_Id")
    private Admin admin;
}
```

#### :o: Song.java
```java
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
```

#### :o: Playlist.java
```java
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
```

##### To See Model
:white_check_mark: [Music-Model](https://github.com/Anushri-glitch/Music_Streaming-Service-API/tree/master/src/main/java/com/Shrishti/Music_StreamingServiceAPI/model)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 2. Service - This Layer is used to write the logic of our CRUD operaions.
#### :o: UserService.java
```java
@Service
public class UserService {

    @Autowired
    IUserDao userDao;

    @Autowired
    IAdminDao adminDao;

    @Autowired
    AuthenticateService authenticateService;

    public SignUpOutput SignUp(SignUpInput signUpDto) {

        //Check If User Exist or not based on User Email
        User user = userDao.findFirstByUserEmail(signUpDto.getEmail());

        if(user != null){
            throw new IllegalStateException("User Already Registered!!");
        }
}
```

#### :o: AuthTokenService.java
```java
@Service
public class AuthenticateService {

    @Autowired
    IAuthenticateDao authenticateDao;

    @Autowired
    IAuthenticateAdminDao authenticateAdminDao;

    //Save Token For User
    public void saveToken(AuthTokenUser token) {
        authenticateDao.save(token);
    }
}
```

#### :o: SongService.java
```java
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
}
```

#### :o: PlaylistService.java
```java
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
}
```

#### To See Service
:white_check_mark: [Music-Service](https://github.com/Anushri-glitch/Music_Streaming-Service-API/tree/master/src/main/java/com/Shrishti/Music_StreamingServiceAPI/service)
----------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 3. Controller - This Controller is used to like UI between Model and Service and also for CRUD Mappings.
#### :o: UserController.java
```java
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //User SignUp
    @PostMapping(value = "/signUp")
    public SignUpOutput SignUp(@RequestBody SignUpInput signUpDto){

        return userService.SignUp(signUpDto);
    }
}
```

#### :o: SongController.java
```java
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
}
```

#### :o: PlayListController.java
```java
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
}
```

#### To See Controller
:white_check_mark: [Music-Controller](https://github.com/Anushri-glitch/Music_Streaming-Service-API/tree/master/src/main/java/com/Shrishti/Music_StreamingServiceAPI/controller)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
### :purple_square: 3. Repository : data access object (DAO) is an object that provides an abstract interface to some type of database or other persistence mechanisms.
#### :o: IUserDao.java
```java
@Repository
public interface IUserDao  extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String userEmail);
}
```
#### :o: IAdminDao.java
```java
@Repository
public interface IAdminDao extends JpaRepository<Admin, Integer> {
    Admin findFirstByAdminEmail(String email);
    Admin findByAdminEmail(String signInMail);
}
```

#### :o: IAuthenticateDao.java
```java
@Repository
public interface IAuthenticateDao extends JpaRepository<AuthTokenUser,Integer> {
    AuthTokenUser findByUser(User user);
}
```

#### :o: IAuthenticateAdminDao.java
```java
@Repository
public interface IAuthenticateAdminDao extends JpaRepository<AuthTokenAdmin,Integer> {
    AuthTokenAdmin findByAdmin(Admin admin);
}
```

#### :o: ISongDao.java
```java
@Repository
public interface ISongDao  extends JpaRepository<Song, Integer> {
}
```

#### :o: IPlaylistDao.java
```java
@Repository
public interface IPlaylistDao  extends JpaRepository<Playlist, Integer> {
}
```

#### To See Repository
:white_check_mark: [Music-DAO](https://github.com/Anushri-glitch/Music_Streaming-Service-API/tree/master/src/main/java/com/Shrishti/Music_StreamingServiceAPI/repository)
-------------------------------------------------------------------------------------------------------------------------------------------------------

### :purple_square: 3. DTO : Use This to do SignUp and SignIn for the User
#### :o: SignUpInput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInput {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String contact;
}
```

#### :o: SignUpOutput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpOutput {

    String status;
    String message;
}
```

#### :o: SignInInput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInInput {

    private String signInMail;
    private String signInPassword;
}
```

#### :o: SignInOutput.java

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInOutput {

    private String status;
    private String token;
}
```
------------------------------------------------------------------------------------------------------------------------------------------------------

## :four: DataStructures Used in Project
    1. List
    2. JsonObject
    3. JsonArray
-------------------------------------------------------------------------------------------------------------------------------------------------------
## :five: DataBase Response In project

:arrow_right: User table

```sql
  select * from user;
+---------+---------------------+-------------------+----------------------------------+------------+
| user_id | user_email          | user_name         | user_password                    | user_phone |
+---------+---------------------+-------------------+----------------------------------+------------+
|       1 | Anushka12@gmail.com | AnushkaSrivastava | 1C6B2A130FD59CE767D50D0598E9F4D1 | 1234567890 |
|       2 | Pankaj@gmail.com    | PankajBhartiya    | A6E49C7BACFCCB95D75464C4AB82422D | 1234567890 |
|       3 | Viresh@gmail.com    | VireshRathore     | A86BEFDE3786B3633D46A742FEF61721 | 1234567890 |
+---------+---------------------+-------------------+----------------------------------+------------+
```

:arrow_right: Admin Table

```sql
  select * from admin;
+----------+------------------+----------------------------------+-------------+-----------------+
| admin_id | admin_email      | admin_password                   | admin_phone | admin_user_name |
+----------+------------------+----------------------------------+-------------+-----------------+
|        2 | viresh@admin.com | A86BEFDE3786B3633D46A742FEF61721 | 1234567890  | VireshRathore   |
+----------+------------------+----------------------------------+-------------+-----------------+
```

:arrow_right: AuthTokenUser Table

```sql
 select * from auth_token_user;
+----------+--------------------------------------+---------------------+------------+
| token_id | token                                | token_creation_date | fk_user_id |
+----------+--------------------------------------+---------------------+------------+
|        1 | c8a5b6e0-e167-45d4-b3a2-af73239ace38 | 2023-05-21          |          1 |
|        2 | b49a53d8-cc12-4007-83cc-df97358c5c25 | 2023-05-21          |          2 |
|        3 | f18b4dd1-aed4-4a41-b72a-a18347de3396 | 2023-05-21          |          3 |
+----------+--------------------------------------+---------------------+------------+
```

:arrow_right: AuthTokenAdmin Table

```sql
select * from auth_token_admin;
+----------------+--------------------------------------+---------------------------+------------+
| admin_token_id | admin_token                          | admin_token_creation_date | fk_user_id |
+----------------+--------------------------------------+---------------------------+------------+
|              1 | 188bd41e-48d1-4e3c-9e05-6f3b0a8a66c5 | 2023-05-21                |          2 |
+----------------+--------------------------------------+---------------------------+------------+
```

:arrow_right: Song Table

 select * from song;
 ```sql
+---------+--------------------+---------------+---------------------+--------------+
| song_id | song_artist        | song_duration | song_name           | fk_song_name |
+---------+--------------------+---------------+---------------------+--------------+
|       1 | King               | 3:09          | Tu Maan Meri Jaan   |            2 |
|      52 | Shreya Ghoshal     | 3:12          | Ghar More pardesiaa |           52 |
|      53 | Aadesh Shrivastava | 3:12          | Mora Piya           |           52 |
+---------+--------------------+---------------+---------------------+--------------+
```

:arrow_right: Playlist Table

```sql
 select * from playlist;
+-------------+---------------+-------------+
| playlist_id | playlist_name | total_songs |
+-------------+---------------+-------------+
|           2 | My FAv        |           3 |
|          52 | Rang-Root     |           2 |
+-------------+---------------+-------------+
```

----------------------------------------------------------------------------------------------------------------------------------------------------------
## :six: Project Summary
### :o: Generated API's

:white_check_mark: SIGNUP USER : https://localhost:8080/signUp

:white_check_mark: SIGNIN USER : https://localhost:8080/signIn

:white_check_mark: SIGNUP ADMIN : https://localhost:8080/signUpAdmin

:white_check_mark: SIGNIN ADMIN : https://localhost:8080/signInAdmin

:white_check_mark: ADD SONGS : https://localhost:8080/songDetails/song/adminMail/{userEmail}

:white_check_mark: GET ALL SONGS : https://localhost:8080/songDetails/adminEmail/{userEmail}

:white_check_mark: GET SONG BY ID : https://localhost:8080/songDetails/songId/{songId}/adminEmail/{adminEmail}

:white_check_mark: UPDATE SONG : https://localhost:8080/songDetails/update/songId/{songId}/adminMail/{adminEmail}

:white_check_mark: DELETE SONG : https://localhost:8080/songDetails/delete/songId/{songId}/adminEmail/{adminEMail}

:white_check_mark: ADD PLAYLIST : https://localhost:8080/userMail/{userEmail]

:white_check_mark: GET PLAYLIST : https://localhost:8080/RunPlaylist?playlistId={playlistId}&userMail={userEmail}

:white_check_mark: UPDATE PLAYLIST : https://localhost:8080/updatePlaylist?playlistId={playlistId}&userEmail={userEmail}

:white_check_mark: DELETE PLAYLIST : https://localhost:8080/deletePlaylist?playlistId={playlitId}&userEmail={userEmail}

--------------------------------------------------------------------------------------------------------------------------------------------------

## :seven: Project Result
### :o: User & Admin Response

![Screenshot (829)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/fbe0272b-e912-4836-88b0-7a5d4b6d8482)

![Screenshot (830)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/eae92b3a-d67e-4b26-b0c1-96ed20a61c45)

![Screenshot (831)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/90c8ca49-864d-49da-bd29-e9db9bdcb4d1)

![Screenshot (832)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/1b09272f-1220-40d0-a36b-226f98cacf00)

### :o: Song Response

![Screenshot (833)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/caad7892-b45a-4a34-8537-91d5e0300d95)

![Screenshot (834)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/89e2c7e0-4db9-4d11-8f4b-1a52cceca61b)

![Screenshot (835)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/6bca7a76-f23e-473b-9849-e40ff0979cd4)

![Screenshot (836)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/aeaa4137-9c6c-4af7-beed-153f5fc25865)

![Screenshot (837)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/08242f3d-76f6-4300-ba7d-2d7c6ef34863)

### :o: Playlist Response

![Screenshot (839)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/c63b3916-3786-4c8d-bdb2-6bef6266df84)

![Screenshot (838)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/cbbf0f44-cf36-4197-9859-3e3f6638bce4)

![Screenshot (840)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/20c7b657-7faa-4e26-98e2-18f118dc47d2)

![Screenshot (841)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/5f916f8c-deae-482d-8044-23f07b6cc160)

### :world_map: Other Good Projects -
#### :o: [Ecommerce Mangement-Service API](https://github.com/Anushri-glitch/Ecommerce-Application)

#### :o: [Restaurent Management-Service API](https://github.com/Anushri-glitch/RestaurentManagement-Application/tree/master)

#### :o: [Stock Management-Service API](https://github.com/Anushri-glitch/Stock-Management-Application)

#### :o: [Visitor Counter-Service API](https://github.com/Anushri-glitch/Visitor-Counter-Application)

#### :o: [Weather API Calling By JAVA](https://github.com/Anushri-glitch/Weather-Forecast-Application)

#### :o: [Sending Mail By JAVA](https://github.com/Anushri-glitch/SendMail-Application)

:arrow_right: ![Screenshot (844)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/2fe46e6f-0ee4-4e2c-88df-41e30af9f564)
:arrow_right: ![Screenshot (845)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/99416677-8a7b-4f92-bb60-9c7db2da2174)
:arrow_right: ![Screenshot (846)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/bced6929-a532-4509-8db5-1bae7508bcdf)
:arrow_right: ![Screenshot (847)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/42fc552c-c670-4358-ac11-d340dbdf49c0)
:arrow_right: ![Screenshot (848)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/73e7f501-6fad-4acc-83e6-0239c56ef8bd)
:arrow_right: ![Screenshot (849)](https://github.com/Anushri-glitch/Music_Streaming-Service-API/assets/47708011/bcc0169d-e274-49e9-8afa-177400886a6f)
-----------------------------------------------------------------------------------------------------------------------------------------------------





