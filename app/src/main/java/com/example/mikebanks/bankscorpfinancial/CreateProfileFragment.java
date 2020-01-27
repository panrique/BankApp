package com.example.mikebanks.bankscorpfinancial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mikebanks.bankscorpfinancial.Model.Profile;
import com.example.mikebanks.bankscorpfinancial.Model.db.ApplicationDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CreateProfileFragment extends Fragment {

    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtCountry;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;
    Profile userProfile;
    DatabaseReference ref;

    public CreateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Create Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_profile, container, false);

        edtFirstName = rootView.findViewById(R.id.edt_first_name);
        edtLastName = rootView.findViewById(R.id.edt_last_name);
        edtEmail = rootView.findViewById(R.id.edt_email);
        edtCountry = rootView.findViewById(R.id.edt_country);
        edtUsername = rootView.findViewById(R.id.edt_username);
        edtPassword = rootView.findViewById(R.id.edt_password);
        edtPasswordConfirm = rootView.findViewById(R.id.edt_password_confirm);
        Button btnCreateAccount = rootView.findViewById(R.id.btn_create_account);
        userProfile = new Profile();
        ref = FirebaseDatabase.getInstance().getReference().child("Profile");
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userProfile.setFirstName(edtFirstName.getText().toString());
                userProfile.setEmail(edtEmail.getText().toString());
                userProfile.setCountry(edtCountry.getText().toString());
                userProfile.setUsername(edtUsername.getText().toString());
                userProfile.setPassword(edtPassword.getText().toString());

                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                if(!(pattern.matcher(userProfile.getEmail()).matches())) {
                    Toast.makeText(CreateProfileFragment.this.getActivity(), "Try email again",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String regex2 =  "((?=.[a-z])(?=.\\d)(?=.[A-Z])(?=.[@#$%!]).{8,40})";
                if(!(pattern.matcher(userProfile.getPassword()).matches())) {
                    Toast.makeText(CreateProfileFragment.this.getActivity(), "Try password again; include at least one lowercase and one uppercase, one number, special character and longer than 7",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(userProfile.getEmail())) {
                            Toast.makeText(CreateProfileFragment.this.getActivity(), "DB Try email again",
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }
                        ref.child(userProfile.getEmail()).setValue(userProfile);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });



                createProfile();

            }
        });

        ((LaunchActivity) getActivity()).showUpButton();

        return rootView;
    }

    /**
     * method used to create an account
     */
    private void createProfile() {

        ApplicationDB applicationDb = new ApplicationDB( getActivity().getApplicationContext());


        ArrayList<Profile> profiles = applicationDb.getAllProfiles();
        boolean usernameTaken = false;


        for (int iProfile = 0; iProfile < profiles.size(); iProfile++) {
            if (edtUsername.getText().toString().equals(profiles.get(iProfile).getUsername())) {
                usernameTaken = true;
            }
        }

        if (edtFirstName.getText().toString().equals("") || edtLastName.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtCountry.getText().toString().equals("") 
			|| edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("") || edtPasswordConfirm.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.fields_blank, Toast.LENGTH_SHORT).show();
        }

        else if (!(edtPassword.getText().toString().equals(edtPasswordConfirm.getText().toString()))) {
            Toast.makeText(getActivity(), R.string.password_mismatch, Toast.LENGTH_SHORT).show();
        }
        else if (usernameTaken) {
            Toast.makeText(getActivity(), "A User has already taken that username", Toast.LENGTH_SHORT).show();
        }
        else {
            Profile userProfile = new Profile(edtFirstName.getText().toString(), edtLastName.getText().toString(), edtEmail.getText().toString(), 
												edtCountry.getText().toString(), edtUsername.getText().toString(), edtPassword.getText().toString());

            applicationDb.saveNewProfile(userProfile);
            Bundle bundle = new Bundle();
            bundle.putString("Username", userProfile.getUsername());
            bundle.putString("Password", userProfile.getPassword());

            ((LaunchActivity) getActivity()).profileCreated(bundle);

        }


    }
}
