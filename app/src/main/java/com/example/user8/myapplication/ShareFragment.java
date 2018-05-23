package com.example.user8.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    String strDate, strTime, strMsg;
    WebServiceCall wcs = new WebServiceCall();
    JSONObject jsonOb = new JSONObject();
    private Button btn_addTask;
    private TextView txtDate, txtTime;
    private EditText edtTaskName, edtTaskDetails;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_share, container, false);

        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        edtTaskName = v.findViewById(R.id.edtTaskName);
        edtTaskDetails = v.findViewById(R.id.edtTaskDetails);
        btn_addTask = v.findViewById(R.id.btn_addtask);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Adding your task...");

        auth = FirebaseAuth.getInstance();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("selectFn", "fnGetDateTime"));

                try {
                    jsonOb = wcs.makeHttpRequest(wcs.fnGetURL(), "POST", params);
                    strDate = jsonOb.getString("currDate");
                    strTime = jsonOb.getString("currTime");
                    strMsg = "Successfully retrieve date and time from server.";
                } catch (Exception e) {
                    strMsg = "Retrieve data and time from your device...";
                    strDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                    strTime = new SimpleDateFormat("hh:mm a").format(new Date());
                }

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        fnDisplayToastMsg(strMsg);
                        txtDate.setText(strDate);
                        txtTime.setText(strTime);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();

        //when click the button, the data will store in firebase
        btn_addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //table name IDOIDO
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("IDOIDO");

                //get task key
                String key = ref.push().getKey();

                //get data from interface
                final String m1 = edtTaskName.getText().toString();
                final String m2 = edtTaskDetails.getText().toString();
                final String m3 = txtDate.getText().toString();
                final String m4 = txtTime.getText().toString();

                //check validation
                if (!validateForm()) {
                    return;
                }

                //progress
                progressDialog.show();

                //add data
                AddTask myTask = new AddTask(m1, m2, m3, m4, key, "PENDING", "none");

                //when data is inserted successfully
                ref.child(key).setValue(myTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //if success
                        if (task.isSuccessful()) {

                            //progress dissapear
                            progressDialog.cancel();

                            //pop out success message and go to another activity
                            Toast.makeText(getContext(), getString(R.string.add_task_success_msg), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            startActivity(new Intent(getContext(), Main2Activity.class));
                        }
                        //if fail
                        else {

                            //progress disappear
                            progressDialog.cancel();

                            //pop out failed message
                            Toast.makeText(getContext(), getString(R.string.add_task_fail_msg), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return v;
    }

    //function to display message
    public void fnDisplayToastMsg(String strText) {
        Toast tst = Toast.makeText(getContext(), strText, Toast.LENGTH_LONG);
        tst.show();
    }

    //function to check validation
    private boolean validateForm() {
        //initialize the viarable
        boolean valid = true;

        //get data from EditText
        String mDetails = edtTaskDetails.getText().toString();
        String mName = edtTaskName.getText().toString();

        //check if empty
        if (TextUtils.isEmpty(mName)) {
            edtTaskName.setError(getString(R.string.required_msg));
            valid = false;
        }

        if (TextUtils.isEmpty(mDetails)) {
            edtTaskDetails.setText("-");
            return valid;
        }

        //if user input data correctly
        if (mDetails.length() >= 0 && mName.length() > 0) {
            edtTaskName.setError(null);
            edtTaskDetails.setError(null);
            return valid;
        } else {
            Toast.makeText(getContext(), getString(R.string.empty_edittext_msg), Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}


