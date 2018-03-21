package com.example.adity.healthcareapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HealthCareHome extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener firebaseAuthStateListner;
    DatabaseReference databaseReference;
    StorageReference mStorageRef;
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_home);


        //signout=(Button)findViewById(R.id.signoutButton);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();

       // databaseReference=firebaseAuth
        /* signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

            }
        }); */


       // signout=findViewById(R.id.signoutButton);
        firebaseAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();



                if(user!=null){
                    final String UserId= user.getUid();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChild(UserId)){

                                finish();
                                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{

                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }


            }
        };
        firebaseAuth.addAuthStateListener(firebaseAuthStateListner);


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListner);
    }

}
