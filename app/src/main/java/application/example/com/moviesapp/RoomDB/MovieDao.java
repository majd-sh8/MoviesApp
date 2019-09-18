package application.example.com.moviesapp.RoomDB;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import application.example.com.moviesapp.models.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM moviesfav")
    List<Movie> getAllMovieFav();


    @Insert
    void insert(Movie movie);

    @Query("SELECT * FROM moviesfav where title LIKE  :title11 ")
    Movie checkMovie(String title11);

    @Query("DELETE FROM moviesfav")
    public void deleteAllMovie();


    @Query("DELETE FROM moviesfav where title LIKE  :titleD")
    public void deleteM(String titleD);



}
