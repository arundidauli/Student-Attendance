package com.techastrum.attendance.activities.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.CategoryProductAdapter;
import com.techastrum.attendance.activities.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private List<Post> postList;
    private RecyclerView recyclerView_post;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        postList = new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        recyclerView_post = findViewById(R.id.rv_post);
        txt_no_data = findViewById(R.id.txt_no_data);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("post");
        TextView dist_name = findViewById(R.id.dist_name);
        dist_name.setText(Prefs.getInstance(context).GetValue("dist"));

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            super.onBackPressed();
        });
        if (getIntent() != null) {
            GetAlPost(getIntent().getStringExtra(Constants.Post_category));
        }
    }

    private void GetAlPost(String Category) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("is_approve").getValue(Boolean.class), true)) {
                            if (Objects.equals(ds.child(Constants.Post_category).getValue(String.class), Category)) {
                                if (Objects.equals(ds.child(Constants.Post_location).getValue(String.class), Prefs.getInstance(context).GetValue(Constants.Dist))) {
                                    Post post = ds.getValue(Post.class);
                                    postList.add(post);
                                }
                            }
                            //get post by user
                           /* if (Objects.equals(ds.child("user_id").getValue(String.class), firebaseAuth.getUid())){
                                if (Objects.equals(ds.child(Constants.Post_category).getValue(String.class), Category)){

                                    if (Objects.equals(ds.child(Constants.Post_location).getValue(String.class), Prefs.getInstance(context).GetValue(Constants.Dist))){
                                        Post post = ds.getValue(Post.class);
                                        postList.add(post);
                                    }

                                }

                            }*/


                        }

                    }

                }
                CategoryProductAdapter categoryProductAdapter = new CategoryProductAdapter(context, postList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView_post.setLayoutManager(mLayoutManager);
                recyclerView_post.setItemAnimator(new DefaultItemAnimator());
                recyclerView_post.setAdapter(categoryProductAdapter);
                if (postList.size()==0){
                    txt_no_data.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                progressDialog.dismiss();

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);


    }


}
