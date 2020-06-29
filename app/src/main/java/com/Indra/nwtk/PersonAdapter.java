package com.Indra.nwtk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static com.Indra.nwtk.Find_friend.personItems;

public class PersonAdapter extends ArrayAdapter<PersonItem> {


    private static final String LOG_TAG = PersonAdapter.class.getSimpleName();

    public PersonAdapter(@NonNull Context context,ArrayList<PersonItem> personItems) {
        super(context,0,personItems);
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.person_item, parent, false);
        }


        PersonItem currentAndroidFlavor = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.person_name);
        name.setText(currentAndroidFlavor.getName());


        TextView email = (TextView) listItemView.findViewById(R.id.person_email);
        email.setText(String.valueOf(currentAndroidFlavor.getEmail()));


        final Button button = (Button) listItemView.findViewById(R.id.followbutton);
        String button_title=currentAndroidFlavor.getTitle_ofcall();
        button.setText(button_title);

        final int final_postiton=position;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Follow")) {
                    final PersonItem person_follow= getItem(final_postiton);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                    String current_user_email= user.getEmail();
                    final String userId=current_user_email.replace(".",",");
                    final PersonItem person_user=new PersonItem(user.getDisplayName(),user.getEmail(),userId);
                    FirebaseDatabase database =  FirebaseDatabase.getInstance();

                    String other_user_email= person_follow.getEmail();
                    String other_userId=other_user_email.replace(".",",");
                    final DatabaseReference mRef =  database.getReference().child("users").child(userId).child("connection")
                            .child("follow_request_sent").child("follow_request_sent_list").child(other_userId);
                    final DatabaseReference mRef_other =  database.getReference().child("users").child(other_userId).child("connection")
                            .child("follow_request_receive").child("follow_request_receive_list").child(userId);


                    Log.i("fpllowing firebase","Huraaay-----Inside on item click--------------"+userId+"  - "+other_user_email);
////
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            mRef.setValue(person_follow);
                            mRef_other.setValue(person_user);

                            Log.i("fpllowing firebase","Huraaay-------fpllowing firebase--------------");
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                            Log.i("fpllowing firebase","Huraaay-------fpllowing firebase failed    --------------");

                        }
                    };
                    mRef.addListenerForSingleValueEvent(eventListener);


                    button.setText("Request_sent");
                }

                else if (button.getText().equals("Accept")) {


                    // current user detail and id
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                    String current_user_email= user.getEmail();
                    final String userId=current_user_email.replace(".",",");
                    final PersonItem person_user=new PersonItem(user.getDisplayName(),user.getEmail(),userId);


                    FirebaseDatabase database =  FirebaseDatabase.getInstance();

                    // other user detail and id
                    final PersonItem person_follow= getItem(final_postiton);
                    final String other_user_email= person_follow.getEmail();
                    final String other_userId=other_user_email.replace(".",",");


                    //current user references
                    final DatabaseReference mRef_receive =  database.getReference().child("users").child(userId).child("connection")
                            .child("follow_request_receive").child("follow_request_receive_list");

                    final DatabaseReference mRef_follow =  database.getReference().child("users").child(userId).child("connection")
                            .child("follower").child("follower_list");

                    //other person reference
                    final DatabaseReference mRef_other_sent =  database.getReference().child("users").child(other_userId).child("connection")
                            .child("follow_request_sent").child("follow_request_sent_list");

                    final DatabaseReference mRef_other_following =  database.getReference().child("users").child(other_userId).child("connection")
                            .child("following").child("following_list");


                    Log.i("fpllowing firebase","Huraaay-----Inside on item click--------------"+userId+"  - "+other_user_email);


                    removeFromFirebase(mRef_receive,mRef_follow,other_userId);
                    removeFromFirebase(mRef_other_sent,mRef_other_following,userId);

                    button.setText("Accepted");
                }

                else if(button.getText().equals("Remove"))
                {


                    // current user detail and id
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                    String current_user_email= user.getEmail();
                    final String userId=current_user_email.replace(".",",");
                    final PersonItem person_user=new PersonItem(user.getDisplayName(),user.getEmail(),userId);


                    FirebaseDatabase database =  FirebaseDatabase.getInstance();

                    // other user detail and id
                    final PersonItem person_follow= getItem(final_postiton);
                    String other_user_email= person_follow.getEmail();
                    final String other_userId=other_user_email.replace(".",",");


                    //current user references

                    final DatabaseReference mRef_follow =  database.getReference().child("users").child(userId).child("connection")
                            .child("follower").child("follower_list");


                    //other person reference

                    final DatabaseReference mRef_other_following =  database.getReference().child("users").child(other_userId).child("connection")
                            .child("following").child("following_list");



                    Log.i("fpllowing firebase","Huraaay-----Inside on item click--------------"+userId+"  - "+other_user_email);



                    mRef_follow.child(other_userId).removeValue();

                    mRef_other_following.child(userId).removeValue();
                    button.setText("Removed");
                }

            }
        });
        return listItemView;
    }


    private void removeFromFirebase(final DatabaseReference fromPath, final DatabaseReference toPath, final String key) {
        fromPath.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            // Now "DataSnapshot" holds the key and the value at the "fromPath".
            // Let's move it to the "toPath". This operation duplicates the
            // key/value pair at the "fromPath" to the "toPath".
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.child(dataSnapshot.getKey())
                        .setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Log.i("TAG", "onComplete: success");
                                    // In order to complete the move, we are going to erase
                                    // the original copy by assigning null as its value.
                                    fromPath.child(key).removeValue();
                                }
                                else {
                                    Log.e("TAG", "onComplete: failure:" + databaseError.getMessage() + ": "
                                            + databaseError.getDetails());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled: " + databaseError.getMessage() + ": "
                        + databaseError.getDetails());
            }
        });
    }


}

