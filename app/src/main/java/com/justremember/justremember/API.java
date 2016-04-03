package com.justremember.justremember;

import com.justremember.justremember.Entities.Note;
import com.justremember.justremember.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dimko_000 on 03.04.2016.
 */
public interface API {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") long id);
    @GET("users/{id}/notes")
    Call<List<Note>> getNotesByUserId(@Path("id") long id);
}
