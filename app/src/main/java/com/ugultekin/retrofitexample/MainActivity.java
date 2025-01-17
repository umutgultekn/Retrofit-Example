package com.ugultekin.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
//        getComments();
//        createPost();
//        updatePost();
//        deletePost();
    }

    public void getPosts(){

        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId","2");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

//        Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{2,3,6},"id","asc");
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts){
                    String content ="";
                    content+= "ID: " + post.getId() + "\n";
                    content+= "User ID: " + post.getUserId() + "\n";
                    content+= "Title : " + post.getTitle() + "\n";
                    content+= "Text : " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }


    public void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for(Comment comment : comments){
                    String content = "";
                    content+= "ID: " + comment.getId() + "\n";
                    content+= "Post ID: " + comment.getPostId() + "\n";
                    content+= "Name: " + comment.getName() + "\n";
                    content+= "Email: " + comment.getEmail() + "\n";
                    content+= "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }

    public void createPost(){
        Post post = new Post(23,"New Title","New Text");

        Map<String,String> fields = new HashMap<>();
        fields.put("userId","36");
        fields.put("title","New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content+= "Code: " + response.code() + "\n";
                content+= "ID: " + postResponse.getId() + "\n";
                content+= "User ID: " + postResponse.getUserId() + "\n";
                content+= "Title: " + postResponse.getTitle() + "\n";
                content+= "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }

    private  void updatePost(){
        Post post = new Post(12,null,"New Text");

        Map<String,String> headers = new HashMap<>();
        headers.put("Map-Header1","def");
        headers.put("Map-Header2","ghi");

//        Call<Post> call = jsonPlaceHolderApi.putPost("abc",5,post);
        Call<Post> call = jsonPlaceHolderApi.patchPost(headers,5,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content+= "Code: " + response.code() + "\n";
                content+= "ID: " + postResponse.getId() + "\n";
                content+= "User ID: " + postResponse.getUserId() + "\n";
                content+= "Title: " + postResponse.getTitle() + "\n";
                content+= "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
