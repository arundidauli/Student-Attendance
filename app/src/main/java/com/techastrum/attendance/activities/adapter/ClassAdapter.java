package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.StudentClass;
import com.techastrum.attendance.activities.student_attandence.AddStudent;
import com.techastrum.attendance.activities.student_attandence.AddTeacherActivity;
import com.techastrum.attendance.activities.student_attandence.ViewAllClasses;
import com.techastrum.attendance.activities.student_attandence.ViewAllStudentActivity;
import com.techastrum.attendance.activities.student_attandence.ViewAllTeacherActivity;

import java.util.List;

import static com.techastrum.attendance.activities.Util.Constants.Post_category;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<StudentClass> categoryList;


    public ClassAdapter(Context context, List<StudentClass> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_category_name.setText(categoryList.get(position).getCategory_name());
        Glide.with(context).load(categoryList.get(position).getImage_url())
                .placeholder(R.mipmap.fire)
                .into(holder.category_icon);

        /*if (categoryList.get(position).getId().equals("15")){
            Intent intent = new Intent(context, ShopActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }*/

       holder.rl_category.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (position==0){
                   Intent intent = new Intent(context, AddStudent.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==1){
                   Intent intent = new Intent(context, AddTeacherActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==2){
                   Intent intent = new Intent(context, ViewAllClasses.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==3){
                   Intent intent = new Intent(context, ViewAllStudentActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==4){
                   Intent intent = new Intent(context, ViewAllTeacherActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==5){
                   Intent intent = new Intent(context, ViewAllClasses.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==6){
                   Intent intent = new Intent(context, ViewAllStudentActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra(Post_category,categoryList.get(position).getCategory_name());
                   context.startActivity(intent);
               }else if (position==7){
                   Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show();
               }else if (position==8){
                   Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show();

               }


           }
       });


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_category_name;
        private ImageView category_icon;
        private CardView card_select;
        private RelativeLayout rl_category;

        MyViewHolder(View view) {
            super(view);
            rl_category=view.findViewById(R.id.rl_category);
            category_icon=view.findViewById(R.id.category_icon);
            txt_category_name=view.findViewById(R.id.txt_category_name);
        }
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
