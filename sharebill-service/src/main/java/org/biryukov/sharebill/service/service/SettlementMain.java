package org.biryukov.sharebill.service.service;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SettlementMain {

    public static void main1(String[] args) {

        class D {

            public int ci;
            public int pi;

            public double amount;
            public D(int ci, int pi, double amount) {
                this.ci = ci;
                this.pi = pi;
                this.amount = amount;
            }
        }

        /*double[] c = {4,4,4};
        double[] p = {3,3,3,3};*/

        /*double[] c = {3,3,3,3};
        double[] p = {4,4,4};*/

        /*double[] c = {3,1,1,1,4};
        double[] p = {1,1,7,1};*/

        /*double[] c = {1,1,7,1};
        double[] p = {3,1,1,1,4};*/

        double[] c = {1,1,7};
        double[] p = {3,1,1,1};




        List<D> ds = new ArrayList<>();
        int ci = 0;
        for(int pi = 0; pi < p.length; pi++) {

            while (ci < c.length) {
                if (p[pi] > c[ci]) {
                    ds.add(new D(ci, pi, c[ci]));
                    p[pi] -= c[ci];
                    c[ci] = 0;
                    ci++;
                } else if(p[pi] < c[ci]) {
                    ds.add(new D(ci, pi, p[pi]));
                    c[ci] -= p[pi];
                    p[pi] = 0;
                    break;
                } else {
                    ds.add(new D(ci, pi, p[pi]));
                    c[ci] = 0;
                    p[pi] = 0;
                    ci++;
                    break;
                }
            }
        }
        System.out.println(ds);
    }
}
