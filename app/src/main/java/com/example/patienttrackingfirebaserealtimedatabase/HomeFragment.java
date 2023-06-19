package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    TextView chat_dr_btn;
    TextView patient_name;
    View view;
    private ConstraintLayout logout_btn, btn_makeAppointment,btnAddPatientManageProfile, btnCheckNotifications,supportBtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        btnAddPatientManageProfile=view.findViewById(R.id.btnAddPatientManageProfile);
        btnAddPatientManageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(view.getContext(),ManageProfile.class);
                intent.putExtra("view","patient");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btnViewMyReports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(view.getContext(), ViewMyReports.class));

            }
        });

        btn_makeAppointment = view.findViewById(R.id.btn_newAppointments);
        btn_makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), MakeAppointment.class));
            }
        });

        supportBtn = view.findViewById(R.id.supportBtn);
        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Support.class));
            }
        });


        patient_name = view.findViewById(R.id.tvDoctorName);

        chat_dr_btn = view.findViewById(R.id.chat_dr_btn);
        chat_dr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), ChatDr.class));
                //Toast.makeText(view.getContext(), "Testing", Toast.LENGTH_SHORT).show();
            }
        });
        patient_name.setText(SharedPrefManager.getInstance(view.getContext()).getUsername());

        btnCheckNotifications = view.findViewById(R.id.btnAddPatientMedicalProfile);
        btnCheckNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Notifications.class));
            }
        });


        logout_btn = view.findViewById(R.id.logout_btn3);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(view.getContext()).logOut();
                Toast.makeText(view.getContext(), "User Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), Login.class));
            }
        });

        return view;
    }
}