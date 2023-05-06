package org.biryukov.sharebill.service.service.pojo;

import java.util.ArrayList;
import java.util.List;

public class Settlement {

    private List<Debt> debts = new ArrayList<>();

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }
}
