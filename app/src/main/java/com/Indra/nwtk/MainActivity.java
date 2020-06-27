package com.Indra.nwtk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static final int RC_SIGN_IN = 1 ;

    int flag;
    String temp;
    public static Uri photoUrl;
    public static final String ANONYMOUS = "anonymous";

    public static String mUsername,mUserid;

    private frag1 f1;
    private frag2 f2;
    private frag3 f3;
    private frag4 f4;
    private frag5 f5;

    private List<Fragment> fragmentList;

    List<String> title;

    //TextView image,name;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseRefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mMessageDatabaseRefrence = mFirebaseDatabase.getReference().child("users");

        mUsername=ANONYMOUS;

        tabLayout=findViewById(R.id.tabs);
        viewPager=findViewById(R.id.pager);

        f1=new frag1();
        f2=new frag2();
        f3=new frag3();
        f4=new frag4();
        f5=new frag5();
        fragmentList=new ArrayList<>();

        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        fragmentList.add(f4);
        fragmentList.add(f5);

        title = new ArrayList<>();

        title.add("Feed");
        title.add("Trending");
        title.add("Add");
        title.add("Voted");
        title.add("Profile");


        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter pageradapter = new ViewPagerAdapter(getSupportFragmentManager(),0);

        viewPager.setAdapter(pageradapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_chrome_reader_mode_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_whatshot_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_edit_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_face_black_24dp);





        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {                // sign in

                    mUsername=user.getDisplayName();
                    mUserid=user.getUid();
                    final String useremail=user.getEmail();
                    photoUrl = user.getPhotoUrl();

                     temp = "";

                    flag=0;

                    //to fetch all the users of firebase Auth app
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                    DatabaseReference usersdRef = rootRef.child("users");

                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                String email = ds.child("email").getValue(String.class);
                                if(useremail.equals(email))
                                {
                                    flag=1;
                                }

                            }
                            if(flag==0)
                            {

                                String user_email=user.getEmail();
                                String useremail_id = user_email.replace(".",","); // replaces all dots

                                PersonItem person = new PersonItem(user.getDisplayName(),user.getEmail());
                                mMessageDatabaseRefrence.child(useremail_id).child("profile").setValue(person);
                                DatabaseReference mMessageDatabaseRefrence_temp=FirebaseDatabase.getInstance().getReference().child("globle").child("user_detail_by_id");

                                mMessageDatabaseRefrence_temp.child(useremail_id).setValue(person);

                            }
//                            if(!dataSnapshot.exists()) {
//                            }


                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    usersdRef.addListenerForSingleValueEvent(eventListener);




                    Toast.makeText(MainActivity.this,   "Welcome " + user.getDisplayName() , Toast.LENGTH_SHORT).show();


                }
                else
                {              // sign out
                    mUsername=ANONYMOUS;


                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);


                }

            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {


            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }



        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return title.get(position);
        }


    }



}
