package com.techastrum.attendance.activities.student_attandence;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.ViewAttendanceAdapter;
import com.techastrum.attendance.activities.model.Attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAttendanceActivity extends AppCompatActivity {
    private static String TAG=ViewAttendanceActivity.class.getSimpleName();
    private String M_Date,M_Time;
    private Context context;
    private List<Attendance> student_List;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        context=ViewAttendanceActivity.this;
        student_List = new ArrayList<>();
        progressDialog=new ProgressDialog(context);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("attendance");

        if (getIntent()!=null){
            M_Date=getIntent().getStringExtra(Constants.DATE);
            M_Time=getIntent().getStringExtra(Constants.TIME);
        }
        GetStudent(Prefs.getInstance(context).GetValue("student_class"),M_Date);
    }
    private void setRecyclerViewCategory() {
        RecyclerView recyclerView = findViewById(R.id.rv_take_attendance);
        ViewAttendanceAdapter studentAdapter = new ViewAttendanceAdapter(context, student_List);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentAdapter);
    }
    private void GetStudent(String student_class,String m_date) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("student_class").getValue(String.class), student_class)){
                            if (Objects.equals(ds.child("attendance_date").getValue(String.class), m_date)){
                                Attendance student = ds.getValue(Attendance.class);
                                student_List.add(student);
                            }

                        }


                    }
                }
                setRecyclerViewCategory();

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