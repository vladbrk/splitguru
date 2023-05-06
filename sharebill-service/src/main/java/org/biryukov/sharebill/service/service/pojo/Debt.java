package org.biryukov.sharebill.service.service.pojo;

public class Debt {

    private Person debtor;
    private Person recepient;
    private Double amount;

    public Debt(Person debtor, Person recepient, Double amount) {
        this.debtor = debtor;
        this.recepient = recepient;
        this.amount = amount;
    }

    public Person getDebtor() {
        return debtor;
    }

    public void setDebtor(Person debtor) {
        this.debtor = debtor;
    }

    public Person getRecepient() {
        return recepient;
    }

    public void setRecepient(Person recepient) {
        this.recepient = recepient;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
