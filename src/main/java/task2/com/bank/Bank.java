package task2.com.bank;

import org.apache.log4j.Logger;
import task2.com.bank.services.threads.ExchangerThreadOne;
import task2.com.bank.services.threads.ExchangerThreadTwo;
import task2.com.bank.utils.TestUserDataGenerator;

import java.util.concurrent.*;

/**
 * @author Isaev_M.M. on 1/9/2017.
 */
public class Bank {

    private static final Logger logger = Logger.getLogger(Bank.class);


    public static void main(String[] args) {

        String userId;
        if (args.length > 1) {
            userId = TestUserDataGenerator.generate(args[0], Integer.parseInt(args[1]));
        } else {
            userId = "0";
        }

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> scheduledFutureOne = executorService.scheduleAtFixedRate(new ExchangerThreadOne(userId), 1000, 1500, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> scheduledFutureTwo = executorService.scheduleAtFixedRate(new ExchangerThreadTwo(userId), 1500, 1000, TimeUnit.MILLISECONDS);


        try {
            scheduledFutureOne.get(15, TimeUnit.SECONDS);
            scheduledFutureTwo.get(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            executorService.shutdown();
        }


    }


}
