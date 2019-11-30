package com.example.myweight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
private List<User> userList;
        FirebaseFirestore firebaseFirestoreDb;

public UserAdapter(List<User> inputData) {
        userList = inputData;
    }


@NonNull
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        ViewHolder vh = new ViewHolder(v);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        return vh;
        }

@Override
public void onBindViewHolder( ViewHolder holder, int position) {
        holder.tanggal.setText(userList.get(position).getTanggal());
        holder.berat.setText(Double.toString( userList.get(position).getBerat()));
        holder.bmi.setText(Double.toString(userList.get(position).gethasilBMI()));
        holder.step.setText(Integer.toString(userList.get(position).getStep()));
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView bmi;
    public TextView tanggal;
    public TextView berat;
    public TextView step;

    public ViewHolder(View v) {
        super(v);
        tanggal = (TextView) v.findViewById(R.id.col_tanggal);
        berat = (TextView) v.findViewById(R.id.col_berat);
        bmi = (TextView) v.findViewById(R.id.col_bmi);
        step = (TextView) v.findViewById(R.id.col_step);
    }
}

    @Override
    public int getItemCount() {
        return userList.size();
    }
}