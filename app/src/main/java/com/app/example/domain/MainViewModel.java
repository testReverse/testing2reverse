package com.app.example.domain;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.example.ui.items.Contact;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MainRepository repository;
    private MutableLiveData<List<Contact>> contacts;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository(application);
    }


    public MutableLiveData<List<Contact>> getContacts() {
        contacts = repository.getContacts();
        return contacts;
    }

    public void postContactList(){
        if(contacts!=null && contacts.getValue() != null && !contacts.getValue().isEmpty())
        repository.postContacts(contacts.getValue());
    }
}