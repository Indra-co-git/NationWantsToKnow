package com.Indra.nwtk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class frag3 extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    CheckBox ch, ch1, ch2;
    TextView mOptions;
    Button mNext,mPost;
    EditText moption1, moption2, moption3, moption4, mQuestionPost;
    String QuestionPost, option1, option2, option3,option4;
    Spinner mPostType;
    boolean empty = false;

    int optionSelected = 0;

    public frag3() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_frag3, null);
        View view = inflater.inflate(R.layout.fragment_frag3, container, false);

        QuestionPost = "";
        option1 = "";
        option2 = "";
        option3 = "";
        option4 = "";

        ch=(CheckBox)root.findViewById(R.id.two);
        ch1=(CheckBox)root.findViewById(R.id.three);
        ch2=(CheckBox)root.findViewById(R.id.four);
        mOptions = (TextView)root.findViewById(R.id.options);
        mNext = (Button)root.findViewById(R.id.nextButton);
        mPost = (Button)root.findViewById(R.id.postButton);
        moption1 = (EditText)root.findViewById(R.id.option1);
        moption2 = (EditText)root.findViewById(R.id.option2);
        moption3 = (EditText)root.findViewById(R.id.option3);
        moption4 = (EditText)root.findViewById(R.id.option4);
        mQuestionPost = (EditText)root.findViewById(R.id.questionPost);

        moption1.setVisibility(View.GONE);
        moption2.setVisibility(View.GONE);
        moption3.setVisibility(View.GONE);
        moption4.setVisibility(View.GONE);
        mPost.setVisibility(View.GONE);

        mPostType = (Spinner) root.findViewById(R.id.postType);
        List<String> list = new ArrayList<String>();
        list.add("Public");
        list.add("Followers");
        list.add("Group 1");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPostType.setAdapter(dataAdapter);
        Button();
        return root;
    }
    public void Button(){
        ch.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                         if(isChecked) {
                             Toast.makeText(getContext(), ch.getText() + " is selected", Toast.LENGTH_LONG).show();
                             if(ch1.isChecked()) {
                                 ch1.toggle();
                             }
                             if(ch2.isChecked()) {
                                 ch2.toggle();
                             }
                             optionSelected = 2;
                         } else {
                             optionSelected =0;
                         }
                     }
                 }
                );
        ch1.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                         if(isChecked) {
                             Toast.makeText(getContext(), ch1.getText() + " is selected", Toast.LENGTH_LONG).show();
                             if(ch.isChecked()) {
                                 ch.toggle();
                             }
                             if(ch2.isChecked()) {
                                 ch2.toggle();
                             }
                             optionSelected = 3;
                         } else {
                             optionSelected =0;
                         }
                     }
                 }
                );
        ch2.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                         if(isChecked) {
                             Toast.makeText(getContext(), ch2.getText() + " is selected", Toast.LENGTH_LONG).show();
                             if(ch1.isChecked()) {
                                 ch1.toggle();
                             }
                             if(ch.isChecked()) {
                                 ch.toggle();
                             }
                             optionSelected = 4;
                         } else {
                             optionSelected =0;
                         }
                     }
                 }
                );
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionSelected == 0){
                    Toast.makeText(getContext(), "Please select a option", Toast.LENGTH_LONG).show();
                } else {
                    ch.setVisibility(View.GONE);
                    ch1.setVisibility(View.GONE);
                    ch2.setVisibility(View.GONE);
                    mNext.setVisibility(View.GONE);
                    mOptions.setVisibility(View.GONE);
                    moption1.setVisibility(View.VISIBLE);
                    moption2.setVisibility(View.VISIBLE);
                    mPost.setVisibility(View.VISIBLE);
                    if (optionSelected == 3) {
                        moption3.setVisibility(View.VISIBLE);
                    }

                    if (optionSelected == 4) {
                        moption3.setVisibility(View.VISIBLE);
                        moption4.setVisibility(View.VISIBLE);
                    }
                }

            }
            //Toast.makeText(getContext(), "PHP", Toast.LENGTH_LONG).show();
        });
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPost = mQuestionPost.getText().toString();
                option1 = moption1.getText().toString();
                option2 = moption2.getText().toString();
                if (QuestionPost.equals("") || option1.equals("") || option2.equals("")) {
                    Toast.makeText(getContext(), "Please fill all the field", Toast.LENGTH_LONG).show();
                } else {
                    if (optionSelected == 2) {
                        Toast.makeText(getContext(), "Your question is posted", Toast.LENGTH_LONG).show();

                    }
                    if (optionSelected == 3) {
                        option3 = moption3.getText().toString();
                        if(option3.equals("")){
                            Toast.makeText(getContext(), "Please fill all the field", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Your question is posted", Toast.LENGTH_LONG).show();
                        }
                    }

                    if (optionSelected == 4) {
                        option3 = moption3.getText().toString();
                        option4 = moption3.getText().toString();
                        if(option3.equals("") || option4.equals("")){
                            Toast.makeText(getContext(), "Please fill all the field", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Your question is posted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            //Toast.makeText(getContext(), "PHP", Toast.LENGTH_LONG).show();
        });
    }
}
