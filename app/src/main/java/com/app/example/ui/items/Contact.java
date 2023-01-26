package com.app.example.ui.items;

public class Contact {
    private String number;
    private String name;

    public Contact(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public boolean hasNumber() { return number != null && !number.isEmpty();}

    public int getFlagOption(){
        if(number.startsWith("+351")){
            return 1;
        } else if (number.startsWith("+34")){
            return 2;
        } else {
            return 0;
        }
    }
}
