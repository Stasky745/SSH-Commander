package com.ntwk.sshcommander.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ArgsAdapterRV extends RecyclerView.Adapter<ArgsViewHolderRV> {
    LinkedHashMap<String, String> argsMap;
    Context ctx;

    public ArgsAdapterRV(Context context, Map<String, String> map) {
        this.ctx = context;
        this.argsMap = new LinkedHashMap<>(map);
        if(argsMap.size() == 0)
            argsMap.put(null, null);
    }

    @NonNull
    @Override
    public ArgsViewHolderRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View argsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commands_args_item, parent, false);

        return new ArgsViewHolderRV(argsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArgsViewHolderRV holder, int position) {
        String key = new ArrayList<>(argsMap.keySet()).get(position);
        String value = argsMap.get(key) != null ? argsMap.get(key) : "";

        holder.flag.setText(key);
        holder.value.setText(value);

        holder.delete.setOnClickListener(view -> deleteItem(position, key));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(Map<String, String> map){
        argsMap = new LinkedHashMap<>(map);
        argsMap.put(null, null);
        notifyDataSetChanged();
    }

    private void deleteItem(int position, String key) {
        argsMap.remove(key);
        notifyItemRemoved(position);
        if(argsMap.size() == 0) {
            argsMap.put(null, null);
            notifyItemInserted(0);
        }
    }

    public Map<String, String> getArgsMap() {
        return argsMap.entrySet().stream()
                .filter(stringStringEntry -> stringStringEntry.getKey() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public int getItemCount() {
        return argsMap.size();
    }
}
