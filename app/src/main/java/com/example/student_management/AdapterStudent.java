package com.example.student_management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    SQLiteStudent sqLiteStudent;
    Activity context;
    private List<Student> studentList;
    private List<Student> displayStudent;

    public AdapterStudent(Activity context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        displayStudent = new ArrayList<>();
        displayStudent.addAll(studentList);
    }

    public void showAll(){
        displayStudent.clear();
        displayStudent.addAll(studentList);
        notifyDataSetChanged();
    }

    public void search(String keyword){
        displayStudent.clear();
        for(Student s : studentList){
            String code = s.getCode()+"";
            if(s.getName().toLowerCase().contains(keyword.toLowerCase()) || code.contains(keyword)){
                displayStudent.add(s);
            }
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        StudentViewHolder viewHolder = (StudentViewHolder) holder;
        final Student student = displayStudent.get(position);

        viewHolder.txtNo.setText(position+1+"");
        viewHolder.txtCode.setText(student.getCode()+"");
        viewHolder.txtName.setText(student.getName());
        viewHolder.txtDate.setText(student.getDate());
        viewHolder.txtEmail.setText(student.getEmail());
        viewHolder.txtAddress.setText(student.getAddress());

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("studentEdit",  student);
                intent.putExtra("data", bundle);
                context.finish();
                context.startActivity(intent);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  alBuilder = new AlertDialog.Builder(context);
                alBuilder.setTitle("Thông báo!");
                alBuilder.setMessage("Xác nhận xóa!");
                alBuilder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayStudent.remove(position);
                        sqLiteStudent = new SQLiteStudent();
                        sqLiteStudent.delete(student.getCode(), context);
                       notifyDataSetChanged();
                    }
                });
                alBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayStudent.size();
    }

    private class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView txtNo;
        TextView txtCode;
        TextView txtName;
        TextView txtDate;
        TextView txtEmail;
        TextView txtAddress;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public StudentViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.no);
            txtCode = itemView.findViewById(R.id.code);
            txtName = itemView.findViewById(R.id.name);
            txtDate = itemView.findViewById(R.id.date);
            txtEmail = itemView.findViewById(R.id.email);
            txtAddress = itemView.findViewById(R.id.address);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
