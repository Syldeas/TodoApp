package com.example.todoapp;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements TodoAdapter.OnItemClickListener {

    private ArrayList<TodoItem> todoList;
    private TodoAdapter todoAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList, this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouveau nom");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_todo, null);
        builder.setView(dialogView);

        EditText edtNewTodo = dialogView.findViewById(R.id.edtNewTodo);

        builder.setPositiveButton("Ajouter", (dialogInterface, i) -> {
            String newTodo = edtNewTodo.getText().toString();
            if (!newTodo.isEmpty()) {
                todoList.add(new TodoItem(newTodo));
                todoAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Annuler", null);

        builder.create().show();
    }

    @Override
    public void onItemClick(TodoItem item) {
        // Gérer le clic sur un élément ici
        // Par exemple, afficher un message avec le nom de l'élément cliqué
        Toast.makeText(this, "Nom : " + item.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_delete) {
            todoAdapter.deleteSelectedItems();
            return true;
        } else if (itemId == R.id.action_update) {
            int selectedPosition = todoAdapter.getSelectedPosition();
            if (selectedPosition != RecyclerView.NO_POSITION) {
                TodoItem selectedTask = todoList.get(selectedPosition);
                showUpdateDialog(selectedTask);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUpdateDialog(final TodoItem taskToUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mettre à jour le nom");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_todo, null);
        builder.setView(dialogView);

        final EditText edtNewTodo = dialogView.findViewById(R.id.edtNewTodo);
        edtNewTodo.setText(taskToUpdate.getName());

        builder.setPositiveButton("Mettre à jour", (dialogInterface, i) -> {
            String updatedTodo = edtNewTodo.getText().toString();
            if (!updatedTodo.isEmpty()) {
                // Mettre à jour le nom de l'élément ici
                taskToUpdate.setName(updatedTodo);
                todoAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Annuler", null);

        builder.create().show();
    }
}
