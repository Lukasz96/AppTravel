package com.example.lukasz.apptravel.db;

import java.util.List;

public class ListaZakupow {

    private List<ElementDoZakupu> elementDoZakupuList;
    private boolean czySpelniona;

    public ListaZakupow(List<ElementDoZakupu> elementDoZakupuList, boolean czySpelniona) {
        this.elementDoZakupuList = elementDoZakupuList;
        this.czySpelniona = czySpelniona;
    }

    public List<ElementDoZakupu> getElementDoZakupuList() {
        return elementDoZakupuList;
    }

    public void setElementDoZakupuList(List<ElementDoZakupu> elementDoZakupuList) {
        this.elementDoZakupuList = elementDoZakupuList;
    }

    public boolean isCzySpelniona() {
        return czySpelniona;
    }

    public void setCzySpelniona(boolean czySpelniona) {
        this.czySpelniona = czySpelniona;
    }
}
