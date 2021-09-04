package com.linkedin.linkedinclone.recommendation;


import java.util.Random;
import java.util.StringJoiner;

public class Recommendation {

    public double[][] matrix_factorization(double[][] R ,int K , double a, double b) {

            double err;
            double errsquare;
            Random rand = new Random();

            int users = R.length;
            int items = R[0].length;


            double[] row = new double[K];
            double[] col = new double[K];
            double[][] V = new double[users][K];
            double[][] FTr = new double[K][items];

            for (int u = 0; u < users; u++)    //Randomly initialisation of V
                for (int k = 0; k < K; k++) {
                    V[u][k] = rand.nextDouble();
                }

            for (int k = 0; k < K; k++)    //Randomly initialisation of F transpose
                for (int i = 0; i < items; i++) {
                    FTr[k][i] = rand.nextDouble();
                }


            for (int step = 0; step < 5000; step++) {
                for (int u = 0; u < users; u++)
                    for (int i = 0; i < items; i++)
                        if (R[u][i] != -1) { //Not empty
                            for (int k = 0; k < K; k++) {
                                row[k] = V[u][k];
                                col[k] = FTr[k][i];
                            }
                            err = R[u][i] - this.dot(row, col, K);
                            for (int k = 0; k < K; k++) {
                                V[u][k] += a * (2 * err * FTr[k][i] - b * V[u][k]); //Gradient
                                FTr[k][i] += a * (2 * err * V[u][k] - b * FTr[k][i]); //Gradient
                            }
                        }

                errsquare = 0;
                for (int u = 0; u < users; u++)
                    for (int i = 0; i < items; i++)
                        if (R[u][i] != -1) { //Not empty
                            for (int k = 0; k < K; k++) {
                                row[k] = V[u][k];
                                col[k] = FTr[k][i];
                            }
                            err = R[u][i] - this.dot(row, col, K);
                            errsquare += err * err;
                            for(int k = 0 ; k < K ; k++)
                                errsquare += (b/2) * (V[u][k]*V[u][k] + FTr[k][i]*FTr[k][i]);
                        }

                if (errsquare <= 0.001) //Precision 10^-3
                    break;


            }

            double[][] newR = new double[users][items];

            for (int u = 0; u < users; u++)
                for (int i = 0; i < items; i++) {
                    for (int k = 0; k < K; k++) {
                        row[k] = V[u][k];
                        col[k] = FTr[k][i];
                    }
                    newR[u][i] = this.dot(row, col, K);
                }

            return newR;


    }

    public double dot(double[] row, double[] col , int K){
        double product = 0;
        for(int i = 0 ; i < K ; i++)
                product += row[i] * col[i];
        return product;
    }

    public void print(double[][] R){

        for (double[] row : R) {
            StringJoiner sj = new StringJoiner(" | ");
            for (double col : row) {
                sj.add(String.format("%02f", col));
            }
            System.out.println(sj.toString());
        }
    }

}
