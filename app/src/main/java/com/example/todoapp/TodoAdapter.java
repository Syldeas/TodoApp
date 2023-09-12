package com.example.todoapp;




import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.Collection;




public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<TodoItem> todoList;
    private OnItemClickListener listener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public TodoAdapter(ArrayList<TodoItem> todoList, OnItemClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem currentItem = todoList.get(position);
        holder.bind(currentItem, listener, position);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView taskNameTextView;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.nameTextView);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedItems.put(position, !selectedItems.get(position));
                        notifyItemChanged(position);
                    }
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedItems.put(position, isChecked);
                    }
                }
            });
        }

        public void bind(TodoItem item, OnItemClickListener listener, int position) {
            taskNameTextView.setText(item.getName());
            checkBox.setChecked(selectedItems.get(position));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void deleteSelectedItems() {
        ArrayList<TodoItem> itemsToRemove = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.valueAt(i)) {
                int position = selectedItems.keyAt(i);
                itemsToRemove.add(todoList.get(position));
            }
        }
        todoList.removeAll(itemsToRemove);
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(TodoItem item);
    }

    public int getSelectedPosition() {
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.valueAt(i)) {
                return selectedItems.keyAt(i);
            }
        }
        return RecyclerView.NO_POSITION;
    }
}
