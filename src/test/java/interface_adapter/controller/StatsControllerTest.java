package interface_adapter.controller;

import app_business.boundary.IStatInteractor;

import stats.aggregate.ExpenseAggregate;
import stats.aggregate.RevenueAggregate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.*;


/**
 * Tests for the StatsController class. Take note of the mocking of the IStatInteractor interface; this is because we
 * want to isolate the behavior of the interactor from the controller, since we are testing only the controller.
 * Be extremely careful not to test interactor logic in the controller tests, and vice versa.
 */
public class StatsControllerTest {
    static StatsController statsController;

    @DisplayName("StatsControllerTest Class Setup")
    @BeforeAll
    public static void setup() {
        IStatInteractor mockStatInteractor = new MockStatInteractor();
        statsController = new StatsController(mockStatInteractor);
    }

    @Test
    public void testGetRevenue() throws ExecutionException, InterruptedException {
        CompletableFuture<List<RevenueAggregate>> revenue = statsController.getRevenue(60);  // Whether it's 1, 2, or 60, we don't care; it should not affect the logic inside StatsController.
        List<RevenueAggregate> revenueAggregates = revenue.get();

        Assertions.assertEquals(3, revenueAggregates.size());
    }

    @Test
    public void testGetExpenses() throws ExecutionException, InterruptedException {
        CompletableFuture<List<ExpenseAggregate>> expenses = statsController.getExpenses(60);
        List<ExpenseAggregate> expenseAggregates = expenses.get();

        Assertions.assertEquals(3, expenseAggregates.size());
    }

    @DisplayName("StatsControllerTest Class Teardown")
    @AfterAll
    public static void teardown() {
        statsController = null;
    }


    private static class MockStatInteractor implements IStatInteractor {
        @Override
        public List<RevenueAggregate> getRevenue(long horizonMinutes) {
            return List.of(
                    new RevenueAggregate(100),
                    new RevenueAggregate(200),
                    new RevenueAggregate(300)
            );
        }

        @Override
        public List<ExpenseAggregate> getExpenses(long horizonMinutes) {
            return List.of(
                    new ExpenseAggregate(100),
                    new ExpenseAggregate(200),
                    new ExpenseAggregate(300)
            );
        }
    }
}
