package org.biryukov.sharebill.service.service;

import org.biryukov.sharebill.service.jparepo.PersonJpaRepository;
import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.biryukov.sharebill.service.service.pojo.Debt;
import org.biryukov.sharebill.service.service.pojo.Person;
import org.biryukov.sharebill.service.service.pojo.Settlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettlmentServiceImpl implements SettlmentService {


    class DBuff {
        private UUID debtor;
        private UUID recepient;
        private Double amount;

        private boolean deleted = false;

        public DBuff(UUID debtor, UUID recepient, Double amount) {
            this.debtor = debtor;
            this.recepient = recepient;
            this.amount = amount;
        }

        public UUID getDebtor() {
            return debtor;
        }

        public void setDebtor(UUID debtor) {
            this.debtor = debtor;
        }

        public UUID getRecepient() {
            return recepient;
        }

        public void setRecepient(UUID recepient) {
            this.recepient = recepient;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public void delete() {
            deleted = true;
        }

        public boolean isDeleted() {
            return deleted;
        }
    }

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    // code optimization
    private Map<UUID, String> personCache = new HashMap<>();

    @Override
    public Settlement calculate(UUID roomId) {
        Settlement settlement = new Settlement();

        List<Product> products = productJpaRepository.findByRoom(roomId);

        if (products.isEmpty()) {
            return settlement;
        }
        List<DBuff> debts = new ArrayList<>();

        products.forEach((product) -> {
            if (!product.getPayers().isEmpty() && !product.getConsumers().isEmpty()) {


                Map<UUID, Double> p = new LinkedHashMap<>();
                Double payerCosts = product.getPrice() / Double.valueOf(product.getPayers().size());

                product.getPayers().forEach(payer -> {
                    personCache.put(payer.getId(), payer.getName());
                    p.put(payer.getId(), payerCosts);
                });

                Map<UUID, Double> c = new LinkedHashMap<>();
                Double consumerDebt = product.getPrice() / Double.valueOf(product.getConsumers().size());
                product.getConsumers().forEach(consumer -> {
                    personCache.put(consumer.getId(), consumer.getName());
                    c.put(consumer.getId(), consumerDebt);
                });

                UUID[] pkeys = p.keySet().toArray(new UUID[p.size()]);
                UUID[] ckeys = c.keySet().toArray(new UUID[c.size()]);

                for (int pi = 0; pi < pkeys.length; pi++) {
                    if(!p.containsKey(pkeys[pi])) {
                        continue;
                    }
                    for (int ci = 0; ci < ckeys.length; ) {
                        if (!c.containsKey(ckeys[ci])) {
                            ci++;
                            continue;
                        }
                        if(!p.containsKey(pkeys[pi])) {
                            break;
                        }
                        if (p.get(pkeys[pi]) > c.get(ckeys[ci])) {
                            debts.add(new DBuff(ckeys[ci], pkeys[pi], c.get(ckeys[ci])));
                            p.put(pkeys[pi], p.get(pkeys[pi]) - c.get(ckeys[ci]));
                            //c.put(ckeys[ci], 0.0);
                            c.remove(ckeys[ci]);
                            ci++;
                        } else if (p.get(pkeys[pi]) < c.get(ckeys[ci])) {
                            debts.add(new DBuff(ckeys[ci], pkeys[pi], p.get(pkeys[pi])));
                            c.put(ckeys[ci], c.get(ckeys[ci]) - p.get(pkeys[pi]));
                            //p.put(pkeys[pi], 0.0);
                            p.remove(pkeys[pi]);
                            break;
                        } else {
                            debts.add(new DBuff(ckeys[ci], pkeys[pi], p.get(pkeys[pi])));
                            //c.put(ckeys[ci], 0.0);
                            //p.put(pkeys[pi], 0.0);
                            c.remove(ckeys[ci]);
                            p.remove(pkeys[pi]);

                            break;
                        }
                    }
                }
            }
        });


        // settlement optimization
        // if recepient and debtor the same person
        Iterator<DBuff> it = debts.iterator();
        while (it.hasNext()) {
            DBuff dBuff = it.next();
            if (dBuff.getRecepient().equals(dBuff.getDebtor()) || dBuff.getAmount() == 0.0) {
                it.remove();
            }
        }

        // settlement optimization
        // summ the same and negative opposite debts
        for (int i = 0; i < debts.size(); i++) {
            DBuff debt = debts.get(i);
            if (debt.isDeleted()) {
                continue;
            }
            Iterator<DBuff> nestedIt = debts.iterator();
            while (nestedIt.hasNext()) {
                DBuff buff = nestedIt.next();
                if (debt.isDeleted()) {
                    break;
                }
                if (buff.isDeleted()) {
                    nestedIt.remove();
                    continue;
                }
                if (debt != buff) {
                    if (buff.getDebtor().equals(debt.getDebtor())
                            && buff.getRecepient().equals(debt.getRecepient())) {
                        debt.setAmount(debt.getAmount() + buff.getAmount());
                        nestedIt.remove();
                    } else if (buff.getDebtor().equals(debt.getRecepient())
                            && buff.getRecepient().equals(debt.getDebtor())) {
                        if (debt.getAmount() > buff.getAmount()) {
                            debt.setAmount(debt.getAmount() - buff.getAmount());
                            nestedIt.remove();
                        } else if (debt.getAmount() < buff.getAmount()) {
                            buff.setAmount(buff.getAmount() - debt.getAmount());
                            debt.delete();
                        } else  {
                            debt.delete();
                            nestedIt.remove();
                        }
                    }
                }
            }
        }
        // because in prev step cant remove and make debt.delete()
        it = debts.iterator();
        while (it.hasNext()) {
            if (it.next().isDeleted()) {
                it.remove();
            }
        }



        // settlement optimization
        // recepient and debtor the same person on different debts (for reducing debt price)
        for (int i = 0; i < debts.size(); i++) {
            DBuff debt = debts.get(i);

            ListIterator<DBuff> nestedIt = debts.listIterator();
            while (nestedIt.hasNext()) {
                DBuff buff = nestedIt.next();
                if (debt != buff && debt.getRecepient().equals(buff.getDebtor())) {
                    if (debt.getAmount() >= buff.getAmount()) {
                        debt.setAmount(debt.getAmount() - buff.getAmount());
                        buff.setDebtor(debt.getDebtor());
                    } else if (debt.getAmount() < buff.getAmount()) {
                        debt.setRecepient(buff.getRecepient());
                        buff.setAmount(buff.getAmount() - debt.getAmount());
                    }
                }
            }
        }

        // settlement optimization
        // remove zero amount
        it = debts.iterator();
        while (it.hasNext()) {
            DBuff dBuff = it.next();
            if (dBuff.getAmount() == 0.0) {
                it.remove();
            }
        }


        debts.forEach(dBuff -> {
            //org.biryukov.sharebill.service.jparepo.entity.Person pd = personJpaRepository.findById(dBuff.getDebtor()).get();
            Person debtor = new Person(dBuff.getDebtor(), personCache.get(dBuff.getDebtor()));
            //org.biryukov.sharebill.service.jparepo.entity.Person pr = personJpaRepository.findById(dBuff.getRecepient()).get();
            Person recepient = new Person(dBuff.getRecepient(), personCache.get(dBuff.getRecepient()));

            Debt d = new Debt(debtor, recepient, dBuff.getAmount());

            settlement.getDebts().add(d);
        });



        return settlement;
    }
}
