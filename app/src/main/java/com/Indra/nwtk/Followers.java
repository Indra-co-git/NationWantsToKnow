package com.Indra.nwtk;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;

import ru.nikartm.support.BadgePosition;

public class Followers extends AppCompatActivity {

    String title;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        title = getIntent().getData().toString();
        setTitle(title);


        ArrayList<PersonItem> personItems = new ArrayList<>();
        personItems.add(new PersonItem("Aman Gutpa","ag597482@gmail.com"));
        personItems.add(new PersonItem("Shalini Verma","ag597482@gmail.com"));
        personItems.add(new PersonItem("Afsdsd Fafs","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman fdsafs","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Grerae","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Gaegaf","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Refea","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Gutpa","ag597482@gmail.com"));
        personItems.add(new PersonItem("Shalini Verma","ag597482@gmail.com"));
        personItems.add(new PersonItem("Afsdsd Fafs","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman fdsafs","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Grerae","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Gaegaf","ag597482@gmail.com"));
        personItems.add(new PersonItem("Aman Refea","ag597482@gmail.com"));


        PersonAdapter personAdapter=new PersonAdapter(this,personItems);

        ListView listView=findViewById(R.id.persons_list);



        listView.setAdapter(personAdapter);






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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}