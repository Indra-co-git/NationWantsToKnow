package com.Indra.nwtk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Find_friend extends AppCompatActivity {

    String title;
    Button button;


    String fsn="";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseRefrence;
    ArrayList<PersonItem> personItems = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        title = "Find Friend";
        setTitle(title);


        ListView listView =findViewById(R.id.persons_list);

        find_friend_list_update();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final PersonItem person_follow= (PersonItem) parent.getItemAtPosition(position);
//
//
//
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
//                String current_user_email= user.getEmail();
//                final String userId=current_user_email.replace(".",",");
//
//                FirebaseDatabase database =  FirebaseDatabase.getInstance();
//
//                String other_user_email= person_follow.getEmail();
//                String other_userId=other_user_email.replace(".",",");
//                final DatabaseReference mRef =  database.getReference().child("users").child(userId).child("connection").child("Following").child("follower_list").child(other_userId);
//
//
//                Log.i("fpllowing firebase","Huraaay-----Inside on item click--------------"+userId+"  - "+other_user_email);
////
//                ValueEventListener eventListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        mRef.setValue(person_follow);
//                        Log.i("fpllowing firebase","Huraaay-------fpllowing firebase--------------");
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//
//                        Log.i("fpllowing firebase","Huraaay-------fpllowing firebase failed    --------------");
//
//                    }
//                };
//                mRef.addListenerForSingleValueEvent(eventListener);
//
//            }
//        });


    }

    private void find_friend_list_update() {

//to fetch all the users of firebase Auth app
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference usersdRef = rootRef.child("globle").child("user_detail_by_id");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("email").exists())
                    { String email = ds.child("email").getValue(String.class);
                        String name = ds.child("name").getValue(String.class);
                        String uid=ds.getKey();
                        Log.d("TAG", name);
                        Log.d("TAG", email);

                        Log.d("TAG--------------qwef--", uid);
                        personItems.add(new PersonItem(name,email,uid));}

                }
                PersonAdapter personAdapter=new PersonAdapter(Find_friend.this,personItems);
                ListView listView=findViewById(R.id.persons_list);
                listView.setAdapter(personAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.follow, menu);

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.menulogo);
        if(title.equals("Followers")) {
            item.setIcon(R.drawable.ic_people_outline_black_24dp);
        }
        else
            item.setIcon(R.drawable.ic_person_add_black_24dp);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulogo:
                Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
                if(title.equals("Following"))
                {
                    item.setVisible(true);


                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference usersdRef = rootRef.child("users");
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String name = ds.child("name").getValue(String.class);
                                fsn=fsn+name+" ";
                                Log.d("TAG", name);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    usersdRef.addListenerForSingleValueEvent(eventListener);


                    Toast.makeText(this,fsn,Toast.LENGTH_SHORT).show();


                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}