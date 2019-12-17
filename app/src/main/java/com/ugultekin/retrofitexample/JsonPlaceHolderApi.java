package com.ugultekin.retrofitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

//    // query girip userId ye eşit olanları gösteriyor
//    @GET("posts")
//    Call<List<Post>> getPost(@Query("userId") int userId);


//    // query girip userId ye eşit olanları gösteriyor
//    @GET("posts")
//    Call<List<Post>> getPost(
//            @Query("userId") int userId,
//            @Query("_sort") String sort,
//            @Query("_order") String order
//    );



    // eger birden fazla userId sıralamak istiyorsak array kullanabiliriz.
    // int yerine Integer kullanırsak null bırakabiliriz.
    @GET("posts")
    Call<List<Post>> getPost(
            @Query("userId") Integer userId[],
            @Query("_sort") String sort,
            @Query("_order") String order
    );


    // eger birden fazla userId sıralamak istiyorsak array kullanabiliriz.
    // int yerine Integer kullanırsak null bırakabiliriz.
    @GET("posts")
    Call<List<Post>> getPost(
            @QueryMap Map<String,String> parameters);


    // id ye gore veri cekiyoruz
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);


    // veri gonderiyoruz
    @POST("posts")
    Call<Post> createPost(@Body Post post);




    // @Query direkt url  --  @Field direkt http body
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") int title,
            @Field("body") int text
    );


    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);


    // PUT butun alanları degistirir
    @Headers({"Static-Header1: 123", "Static-Header2: 456"})
    @PUT("posts/{id}")
    Call<Post> putPost(@Header("Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post);


    // PATCH sadece girdigimiz alanları degistirir
    @PATCH("posts/{id}")
    Call<Post> patchPost(@HeaderMap Map<String, String> headers,
            @Path("id") int id,
            @Body Post post);


    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
