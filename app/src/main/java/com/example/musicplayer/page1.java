package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

class Song {
    private String title;

    private String albumimg;
   private String songurl;

   public Song(){

   }
    public Song(String title, String song, String album) {
        this.title = title;
        this.songurl=song;
        this.albumimg = album;

    }

    // Getters and setters for the Song class properties

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSong() {
        return songurl;
    }

    public void setSong(String song) {
        this.songurl = song;
    }


    public String getAlbum() {
        return albumimg;
    }

    public void setAlbum(String album) {
        this.albumimg = album;
    }


}

public class page1 extends AppCompatActivity {

    TextView email,name;
    Button logout;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    ListView listView;
    ArrayList<String> titlelist=new ArrayList<>();
    ArrayList<String>imgurllist=new ArrayList<>();
    ArrayList<String>songurllist=new ArrayList<>();
    ArrayAdapter<String>arrayAdapter;
    JcPlayerView jcplayerView ;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    //FireS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        email=findViewById(R.id.email);
        name=findViewById(R.id.user);
        logout=findViewById(R.id.logout);
        listView=findViewById(R.id.mylistview);

        jcplayerView = (JcPlayerView) findViewById(R.id.jcplayer);


       // recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acc=GoogleSignIn.getLastSignedInAccount(this);
        if (acc!=null){
            String user= acc.getDisplayName();
            String mail= acc.getEmail();

            name.setText(user);
            email.setText(mail);
        }
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        retriveData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                jcplayerView.playAudio(jcAudios.get(pos));
                jcplayerView.setVisibility(view.VISIBLE);
            }
        });






    }

    private void retriveData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

           db.collection("songs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                       Song obj= documentSnapshot.toObject(Song.class);

                       String title = documentSnapshot.getString("title");
                       String song = documentSnapshot.getString("songurl");
                       String album = documentSnapshot.getString("imgurl");
                       jcAudios.add(JcAudio.createFromURL(title,song));

                       titlelist.add(title);
                       songurllist.add(song);
                       imgurllist.add(album);


                   }
                   arrayAdapter=new ArrayAdapter<String>(page1.this, android.R.layout.simple_list_item_1,titlelist);
                   listView.setAdapter(arrayAdapter);
                   jcplayerView.initPlaylist(jcAudios,null);


               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(page1.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
               }
           });




    }

    public void logout(View v){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(page1.this,MainActivity.class));
            }
        });
    }

}