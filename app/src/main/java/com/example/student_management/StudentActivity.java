package com.example.student_management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentActivity extends AppCompatActivity {
    TextView txtTitle;
    Button btnSave, btnCancel;
    EditText edtCode, edtName, edtDate, edtEmail, edtAddress;

    SQLiteStudent sqLiteStudent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        init();
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("data");
        if(bundle != null){
            readData();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle != null){
                    update();
                }else{
                    insert();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    private void insert(){
        Student s = checkData();
        if(s != null ){
            if(checkData(s.getCode())){
                sqLiteStudent.insert(this, s);
                dialogSuccess();
            }else{
                dialogError("Mã số sinh viên đã tồn tại.\nHãy nhập lại!");
            }
        }


    }

    private Student checkData(){
        Student s = null;

        String code = edtCode.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if(code.equals("") || name.equals("") || date.equals("") || email.equals("") || address.equals("")){
            dialogError("Hãy nhập đầy đủ thông tin!");
        }else{
            int c = Integer.parseInt(code);
            s = new Student(c, name, date, email, address);
        }
        return s;
    }

    private boolean checkData(int code){
        Student s = sqLiteStudent.findOne(this, code);
        return s == null;
    }

    private void update(){
        Student s = checkData();
        if(s != null) {
            sqLiteStudent.update(this, s);
            dialogSuccess();
        }
    }

    private void dialogError(String message){
        AlertDialog.Builder  alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Thông báo!");
        alBuilder.setMessage(message);
        alBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alBuilder.show();
    }

    private void dialogSuccess(){
        AlertDialog.Builder  alBuilder = new AlertDialog.Builder(this);
        alBuilder.setCancelable(false);
        alBuilder.setTitle("Thông báo!");
        alBuilder.setMessage("Thành công");
        alBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
        alBuilder.show();
    }

    private void cancel(){
        Intent i = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(i);
    }

    private void readData(){
        txtTitle.setText("Cập nhập thông tin sinh viên");
        Student s = (Student) bundle.getSerializable("studentEdit");
        edtCode.setText(s.getCode()+ "");
        edtCode.setEnabled(false);
        edtName.setText(s.getName());
        edtDate.setText(s.getDate());
        edtEmail.setText(s.getEmail());
        edtAddress.setText(s.getAddress());
    }

    private void init(){
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        txtTitle = findViewById(R.id.title);
        edtCode = findViewById(R.id.txtCode);
        edtName = findViewById(R.id.txtName);
        edtDate = findViewById(R.id.txtDate);
        edtEmail = findViewById(R.id.txtEmail);
        edtAddress = findViewById(R.id.txtAddress);

        sqLiteStudent = new SQLiteStudent();
    }

    private void setDate() {
        final Calendar calendar = Calendar.getInstance();
        final int date = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(dateFormat.format(calendar.getTime()));
            }
        },y,m,date);
        dialog.show();
    }

}
