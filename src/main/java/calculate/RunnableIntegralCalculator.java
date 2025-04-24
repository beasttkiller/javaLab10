package calculate;


import com.sun.tools.javac.Main;
import org.example.javalab10.HelloController;

import java.util.function.DoubleUnaryOperator;

public class RunnableIntegralCalculator implements Runnable{
    private IntegralCalculator integralCalculator;
    private HelloController helloController;

    public RunnableIntegralCalculator(long starting, double end, int localN, DoubleUnaryOperator f, HelloController helloController) {
        this.integralCalculator = new IntegralCalculator(starting, end, localN, f);
        this.helloController = helloController;
    }

    @Override
    public void run() {
        double result = integralCalculator.calculate();
        helloController.sendResult(result);
    }
}
