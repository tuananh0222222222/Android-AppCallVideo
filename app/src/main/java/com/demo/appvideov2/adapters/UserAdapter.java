package com.demo.appvideov2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.appvideov2.R;
import com.demo.appvideov2.models.User;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    static  class  UserViewHolder extends RecyclerView.ViewHolder {
        TextView textFirstChar ,textUser,textEmail;
        ImageView imageAudioMeetting,imageVideoMetting;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
           textFirstChar = itemView.findViewById(R.id.textFirstChar);
           textUser = itemView.findViewById(R.id.textUser);
           textEmail = itemView.findViewById(R.id.textEmail);
            imageAudioMeetting = itemView.findViewById((R.id.imageAudioMetting));
            imageVideoMetting = itemView.findViewById((R.id.imageVideoMetting));

        }

        void setUserData(User user){
            textFirstChar.setText(user.firstName.substring(0,1));
            textUser.setText(String.format("%s %s ",user.firstName,user.lastName));
            textEmail.setText(user.email);
        }
    }




}
