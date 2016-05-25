package com.liu.dave.stories;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liu.dave.stories.databinding.ItemLeftDrawerBinding;
import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.viewmodel.LeftDrawerViewModel;

import java.util.ArrayList;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class LeftDrawerAdapter extends RecyclerView.Adapter<LeftDrawerAdapter.ViewHolder> {
    private ArrayList<Person> mPersons = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mListener;

    public LeftDrawerAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLeftDrawerBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_left_drawer, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindViewModel(mPersons.get(position));
        holder.binding.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLeftDrawerBinding binding;

        public ViewHolder(ItemLeftDrawerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bindViewModel(Person person) {
            LeftDrawerViewModel viewModel = binding.getViewModel();
            if (viewModel == null) {
                viewModel = new LeftDrawerViewModel(person);
                binding.setViewModel(viewModel);
            } else {
                viewModel.setPerson(person);
            }
        }
    }

    public void setPersons(ArrayList<Person> persons) {
        mPersons = persons;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
