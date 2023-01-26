package com.app.example.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.example.R;
import com.app.example.extras.Constants;
import com.app.example.ui.items.Contact;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private Context context;

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.contacts = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView numberTextView;
        private LinearLayout contactLayout;
        private Context context;

        public ContactViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contact_name);
            numberTextView = itemView.findViewById(R.id.contact_phone);
            contactLayout = itemView.findViewById(R.id.contact_layout);
            this.context = context;
        }

        public void bind(Contact contact) {
            nameTextView.setText(contact.getName());
            if (contact.hasNumber()) {
                numberTextView.setText(contact.getNumber());
                drawBGbyCountry(contact);
            } else {
                numberTextView.setVisibility(View.GONE);
            }
        }

        private void drawBGbyCountry(Contact contact) {

            String bgUrl = chooseBGbyCountry(contact);

            Glide.with(context).load(bgUrl)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            contactLayout.setBackground(resource);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }

        private String chooseBGbyCountry(Contact contact) {
            String flagUrl = "";
            switch (contact.getFlagOption()) {
                case Constants.PT_COUNTRY:
                    flagUrl = "https://img.freepik.com/foto-gratis/bandera-portugal_1401-202.jpg?w=740&t=st=1674757600~exp=1674758200~hmac=b48f872b3257f7f2b2349a355f45bb9ae418ef91a9b22d514bc597e691332daf";
                    break;
                case Constants.ES_COUNTRY:
                    flagUrl = "https://img.freepik.com/foto-gratis/primer-plano-bandera-ondeante-realista-espana_181624-14702.jpg?w=900&t=st=1674757780~exp=1674758380~hmac=540bb1a149b5ca162ce8a54abedc3550bf15a6ad88124153887a40220c3c300d";
                    break;
                default:
                    flagUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_the_United_Nations.svg/2560px-Flag_of_the_United_Nations.svg.png";
                    break;
            }
            return flagUrl;
        }

    }
}

