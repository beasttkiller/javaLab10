package org.example.javalab10;

import calculate.RunnableIntegralCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.DoubleUnaryOperator;

public class HelloController {
    @FXML
    private TextField aField, bField, nField, threadsField;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onCalculateButtonClick() {
        double a = Double.parseDouble(aField.getText());
        double b = Double.parseDouble(bField.getText());
        int n = Integer.parseInt(nField.getText());
        DoubleUnaryOperator f = x -> Math.pow(Math.sin(x), 2) / Math.cos(x);

        long start = System.currentTimeMillis();

        int numberOfThreads = Integer.parseInt(threadsField.getText());
        double delta = (b - a) / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            double starting = a + i * delta;
            double end = starting + delta;
            int localN = n / numberOfThreads;

            Thread thread = new Thread(new RunnableIntegralCalculator(start, end, localN, f, this));
            thread.start();
        }

        try {
            synchronized (this) {
                while (finishedThreads < numberOfThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long finish = System.currentTimeMillis();

        resultLabel.setText(String.format("Результат інтегрування: %.6f\nЧас виконання: %d мс", totalSum, (finish - start)));
    }

    public synchronized void sendResult(double result) {
        totalSum += result;
        finishedThreads++;
        notify();
    }
    double totalSum = 0;
    int finishedThreads = 0;
}

