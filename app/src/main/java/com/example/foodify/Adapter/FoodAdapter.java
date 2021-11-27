package com.example.foodify.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.Utils.DBHelper;
import com.example.foodify.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    CategoryById array;
    ArrayList<FoodItem> item;
    private Context context;
    DBHelper DB;
    int quantity = 0;
    ElegantNumberButton elegantNumberButton;

    public FoodAdapter(CategoryById array) {
        this.array = array;
        item = array.getItems();


    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        context = view.getContext();
        return new FoodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.txtmenuname.setText(item.get(position).getName());
        holder.txtmenuprice.setText(Integer.toString(item.get(position).getPrice()) + " $");
        holder.txtdesc.setText((item.get(position).getDescription()));
        DB = new DBHelper(context);
        Picasso.with(context).load(item.get(position).getImage()).into(holder.txtmenuimage);
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                LayoutInflater inflater=LayoutInflater.from(context);
                View view1=inflater.inflate(R.layout.add_item_quantity,null);
                elegantNumberButton=view1.findViewById(R.id.quantity);

                builder.setView(view1)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int id= Integer.parseInt(item.get(position).getId());

                                        String q=elegantNumberButton.getNumber();
                                        quantity=Integer.parseInt(q);
                                        Toast.makeText(context, q, Toast.LENGTH_SHORT).show();
                                        Boolean checkinsert=DB.insertData(id,quantity);
                                        if(checkinsert)
                                        {
                                            Toast.makeText(context, "Added Peacefuly", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {

                                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }

                                });
                builder.create();
                builder.show();

            }
        });

    }




    //
    @Override
    public int getItemCount() {
        return item.size();
    }

}
