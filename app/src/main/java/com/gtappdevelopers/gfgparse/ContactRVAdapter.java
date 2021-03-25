package com.gtappdevelopers.gfgparse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ViewHolder> {
    //creating variables for context and array list.
    private Context context;
    private ArrayList<ContactsModal> contactsModalArrayList;

    //creating a constructior.
    public ContactRVAdapter(Context context, ArrayList<ContactsModal> contactsModalArrayList) {
        this.context = context;
        this.contactsModalArrayList = contactsModalArrayList;
    }

    @NonNull
    @Override
    public ContactRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing our layout file for displaying our card item
        return new ContactRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.contacts_rv_item, parent, false));

    }

    //below mwthod is use for filtering data in our array list.
    public void filterList(ArrayList<ContactsModal> filterllist) {
        //on below line we are passing filtered array list in our original array list
        contactsModalArrayList = filterllist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRVAdapter.ViewHolder holder, int position) {
        //getting data from array list in our modal.
        ContactsModal modal = contactsModalArrayList.get(position);
        //on below line we are setting data to our text view.
        holder.contactTV.setText(modal.getUserName());
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        //below text drawable is a circular.
        TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                .width(100)  // width in px
                .height(100) // height in px
                .endConfig()
                //as we are building a circular drawable we are calling a build round method.
                //in that method we are passing our text and color.
                .buildRound(modal.getUserName().substring(0, 1), color);
        //setting image to our image view on below line.
        holder.contactIV.setImageDrawable(drawable2);
        //on below line we are adding on click listner to our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening a new activity and passing data to it.
                Intent i = new Intent(context, ContactDetailActivity.class);
                i.putExtra("name", modal.getUserName());
                i.putExtra("contact", modal.getContactNumber());
                //on below line we are starting a new activity,
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //on below line creating a variable for our image view and text view.
        private ImageView contactIV;
        private TextView contactTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing our image view and text view.
            contactIV = itemView.findViewById(R.id.idIVContact);
            contactTV = itemView.findViewById(R.id.idTVContactName);
        }
    }
}
