package com.app.example.domain;

import com.app.example.ui.items.Contact;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
        @POST("/api/contacts")
        Call<ResponseBody> sendContacts(@Body List<Contact> contacts);
}
