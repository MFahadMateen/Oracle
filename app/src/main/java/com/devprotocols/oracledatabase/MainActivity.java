package com.devprotocols.oracledatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.devprotocols.oracledatabase.adapters.MyAdapter;
import com.devprotocols.oracledatabase.databinding.ActivityMainBinding;
import com.devprotocols.oracledatabase.models.MyModel;
import com.devprotocols.oracledatabase.utils.BaseUtils;
import com.devprotocols.oracledatabase.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<MyModel> myModelList = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initClickListeners();
    }

    private void initClickListeners() {
        binding.btnConnect.setOnClickListener(view -> validateFields());
    }

    private void initViews() {
        Paper.init(this);
        binding.etIp.setText(Paper.book().read(Constants.ipAddress));
        binding.etUsername.setText(Paper.book().read(Constants.username));
        binding.etPassword.setText(Paper.book().read(Constants.password));
        binding.etPortNumber.setText(Paper.book().read(Constants.portNumber));
        binding.etServiceName.setText(Paper.book().read(Constants.serviceName));

        adapter = new MyAdapter(this);
        binding.rvData.setHasFixedSize(true);
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        binding.rvData.setAdapter(adapter);
    }

    private void validateFields() {
        if (Objects.requireNonNull(binding.etIp.getText()).toString().matches("")) {
            BaseUtils.showToast(this, "Server name or IP address is required!");
        } else if (Objects.requireNonNull(binding.etUsername.getText()).toString().matches("")) {
            BaseUtils.showToast(this, "Username is required!");
        } else if (Objects.requireNonNull(binding.etPassword.getText()).toString().matches("")) {
            BaseUtils.showToast(this, "Password is required!");
        } else if (Objects.requireNonNull(binding.etPortNumber.getText()).toString().matches("")) {
            BaseUtils.showToast(this, "Port Number is required!");
        } else if (Objects.requireNonNull(binding.etServiceName.getText()).toString().matches("")) {
            BaseUtils.showToast(this, "Service Name is required!");
        } else {
            Paper.book().write(Constants.ipAddress, binding.etIp.getText().toString());
            Paper.book().write(Constants.username, binding.etUsername.getText().toString());
            Paper.book().write(Constants.password, binding.etPassword.getText().toString());
            Paper.book().write(Constants.portNumber, binding.etPortNumber.getText().toString());
            Paper.book().write(Constants.serviceName, binding.etServiceName.getText().toString());
            binding.progressBar.setVisibility(View.VISIBLE);
            connectToDatabase();
        }
    }

    private void connectToDatabase() {
        try {

            Class.forName("oracle.jdbc.OracleDriver");
            // METHOD #1
//            String dbURL1 = "jdbc:oracle:thin:tiger/scott@localhost:1521:productDB";

            String ipAddress = Objects.requireNonNull(binding.etIp.getText()).toString();
            String portNumber = Objects.requireNonNull(binding.etPortNumber.getText()).toString();
            String serviceName = Objects.requireNonNull(binding.etServiceName.getText()).toString();
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            AsyncTask.execute(() -> {
                String dbURL1 = "jdbc:oracle:thin:" + username + "/" + password + "@" + ipAddress + ":" + portNumber + ":" + serviceName;
                Connection conn1 = null;
                try {
                    conn1 = DriverManager.getConnection(dbURL1.trim());
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                if (conn1 != null) {
                    Statement stmt;
                    myModelList = new ArrayList<>();
                    try {
                        stmt = conn1.createStatement();
                        String query = "SELECT article_code, barcode, descrption_english, description_arabic, price FROM spar_ecom_items WHERE barcode = '628101812018' AND site = 40";
//                        String query = "select Article_Code, Barcode, Descrption_English, Description_Arabic, Price from SPAR_ECOM_Items where Barcode = '628101812018' and Site = 40";
//                        String query = "select * from SPAR_ECOM_Items";
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next())
                            myModelList.add(new MyModel(String.valueOf(rs.getInt(1)), String.valueOf(rs.getInt(2)), String.valueOf(rs.getInt(3)), String.valueOf(rs.getInt(4)), String.valueOf(rs.getInt(5))));

//                            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
//                        conn1.close();
                        adapter.setList(myModelList);
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        BaseUtils.showToast(MainActivity.this, "Connected");
                    });

                } else {
                    runOnUiThread(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        BaseUtils.showToast(MainActivity.this, "Failed to connect!");
                    });

                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
            BaseUtils.showToast(this, ex.toString());
        }
    }
}