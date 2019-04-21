package com.example.hai;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hai.models.Meta;

import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.ViewHolder> {


    // Esta es una lista de valores de tipo Meta. Son mis datos.
    private List<Meta> listMeta;
    // Este es un valor de tipo interfaz que controla un click en un elemento de la lista
    private OnItemClickListener listener;

    public MetaAdapter(List<Meta> listMeta, OnItemClickListener listener){
        this.listMeta = listMeta;
        this.listener = listener;
    }

    public MetaAdapter(List<Meta> listMeta){
        this.listMeta = listMeta;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_meta, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Meta meta = listMeta.get(i);
        viewHolder.txtRazon.setText(meta.getRazon());
        viewHolder.txtCantidad.setText(String.valueOf(meta.getCantidad()));
        viewHolder.txtTiempo.setText(String.format("%d días", meta.getTiempo()));
        viewHolder.onClick(meta, listener);
    }

    @Override
    public int getItemCount() {
        return listMeta.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRazon;
        public TextView txtCantidad;
        public TextView txtTiempo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRazon = itemView.findViewById(R.id.txtRazon);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtTiempo = itemView.findViewById(R.id.txtTiempo);
        }

        public void onClick(final Meta meta, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(meta);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Meta meta);
    }
}
