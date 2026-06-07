package com.example.dev_sec_5.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dev_sec_5.R;

import java.util.List;

public class MessageAdapter_ibtissam extends RecyclerView.Adapter<MessageAdapter_ibtissam.MessageViewHolder_ibtissam> {
    
    private List<String> messages_ibtissam;
    
    public MessageAdapter_ibtissam(List<String> messages_ibtissam) {
        this.messages_ibtissam = messages_ibtissam;
    }
    
    @NonNull
    @Override
    public MessageViewHolder_ibtissam onCreateViewHolder(@NonNull ViewGroup parent_ibtissam, int viewType_ibtissam) {
        View view_ibtissam = LayoutInflater.from(parent_ibtissam.getContext())
                .inflate(R.layout.item_message_ibtissam, parent_ibtissam, false);
        return new MessageViewHolder_ibtissam(view_ibtissam);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder_ibtissam holder_ibtissam, int position_ibtissam) {
        String message_ibtissam = messages_ibtissam.get(position_ibtissam);
        holder_ibtissam.tvMessage_ibtissam.setText(message_ibtissam);
    }
    
    @Override
    public int getItemCount() {
        return messages_ibtissam.size();
    }
    
    public void addMessage_ibtissam(String message_ibtissam) {
        messages_ibtissam.add(0, message_ibtissam);
        notifyItemInserted(0);
    }
    
    static class MessageViewHolder_ibtissam extends RecyclerView.ViewHolder {
        TextView tvMessage_ibtissam;
        
        public MessageViewHolder_ibtissam(@NonNull View itemView_ibtissam) {
            super(itemView_ibtissam);
            tvMessage_ibtissam = itemView_ibtissam.findViewById(R.id.tvMessage_ibtissam);
        }
    }
}