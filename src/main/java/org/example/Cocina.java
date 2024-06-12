package org.example;

import java.util.ArrayList;

public class Cocina {
    
    private ArrayList<Platos> platos;

    public Cocina(ArrayList<Platos> platos) {
        this.platos = platos;
    }

    public Cocina(){
        
    }

    public ArrayList<Platos> getPlatos() {
        return platos;
    }

    public void setPlatos(ArrayList<Platos> platos) {
        this.platos = platos;
    }

    public void addPlatos(ArrayList<Platos> platos){

        this.platos.add(plato);

    }

}
