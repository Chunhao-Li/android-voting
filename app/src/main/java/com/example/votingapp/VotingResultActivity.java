package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

public class VotingResultActivity extends AppCompatActivity {
    private TextView votingTitle;
    private TextView votingIdText;
    private String title;
    private String votingId;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0,"Copy");
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("votingId", votingIdText.getText());
        if (manager != null) {
            manager.setPrimaryClip(clipData);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_result);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            title = intent.getExtras().getString(VotingAdapter.RC_VOTING_TITLE);
            votingId = intent.getExtras().getString(VotingAdapter.RC_VOTING_ID);
        }

        // Initialize
        votingTitle = findViewById(R.id.voting_result_title);
        votingIdText = findViewById(R.id.voting_result_id);
        registerForContextMenu(votingIdText);
        votingTitle.setText(title);
        votingIdText.setText(votingId);

    }
}
