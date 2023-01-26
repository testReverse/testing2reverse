package com.app.example.ui;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.example.R;
import com.app.example.ui.items.Contact;
import com.app.example.ui.adapters.ContactAdapter;
import com.app.example.domain.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final int REQUEST_READ_CONTACTS = 123;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private MainViewModel viewModel;
    private boolean granted;



    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        askForContactsPermission();
        if(granted){
            registerObserver();
            setRecyclerView();
        } else {
            Toast.makeText(getContext(), getString(R.string.accept_permission), Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactAdapter(getContext(), getContactList());
        recyclerView.setAdapter(adapter);
    }


    private void registerObserver(){
        if(viewModel!=null){
            viewModel.getContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> contacts) {
                    adapter.setContacts(contacts);
                    viewModel.postContactList();
                }
            });
        }
    }

    public void askForContactsPermission(){
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            granted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            granted = true;
        } else {
            granted = false;
        }
    }

    private List<Contact> getContactList() {
        List<Contact> list = new ArrayList<>();
        return list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }


}