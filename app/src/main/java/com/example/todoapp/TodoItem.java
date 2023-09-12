package com.example.todoapp;


public class TodoItem {
    private String name;
    private boolean isChecked;

    public TodoItem(String name) {
        this.name = name;
        this.isChecked = false; // Par défaut, la case n'est pas cochée
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



    public void setName(String name) {
        this.name = name;
    }




}
