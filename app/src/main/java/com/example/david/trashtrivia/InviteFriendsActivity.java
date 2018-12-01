package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InviteFriendsActivity extends Activity implements View.OnClickListener{

    Button buttonInviteFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        buttonInviteFriend.findViewById(R.id.button_invite_friend);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonInviteFriend){
            System.out.println("Bleh");
        }
    }
}
