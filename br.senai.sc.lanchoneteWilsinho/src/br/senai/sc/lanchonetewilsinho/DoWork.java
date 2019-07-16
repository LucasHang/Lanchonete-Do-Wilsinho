/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho;

import javafx.concurrent.Task;

/**
 *
 * @author Bratva
 */
public class DoWork extends Task<Integer>{
    @Override
    protected Integer call() throws Exception {
        for (int i = 0; i < 2; i++) {
            System.out.println(i + 1);
            updateProgress(i + 1, 10);
            Thread.sleep(500);
            if (isCancelled()) {
                return i;
            }

        }
        return 10;
    }
}
