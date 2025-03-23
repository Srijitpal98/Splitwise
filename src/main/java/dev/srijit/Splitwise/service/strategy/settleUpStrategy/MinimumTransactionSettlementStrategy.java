package dev.srijit.Splitwise.service.strategy.settleUpStrategy;

import dev.srijit.Splitwise.dto.UserAmountDTO;
import dev.srijit.Splitwise.entity.*;

import java.util.*;

public class MinimumTransactionSettlementStrategy implements SettleUpStrategy{
    @Override
    public List<SettlementTransaction> getSettlementTransactions(List<Expense> expenses) {
        HashMap<User, Double> map = getOutstandingBalances(expenses);
        // Comparator for min heap [ascending order]
        Comparator<UserAmountDTO> minHeapComparator = Comparator.comparingDouble(UserAmountDTO::getAmount);
        // Comparator for max heap [descending order]
        Comparator<UserAmountDTO> maxHeapComparator = Comparator.comparingDouble(UserAmountDTO::getAmount).reversed();
        // Max Heap
        PriorityQueue<UserAmountDTO> maxHeap = new PriorityQueue<>(maxHeapComparator);
        // Min Heap
        PriorityQueue<UserAmountDTO> minHeap = new PriorityQueue<>(minHeapComparator);

        for(Map.Entry<User, Double> entry : map.entrySet()) {
            if(entry.getValue() < 0) {
                minHeap.add(new UserAmountDTO(entry.getKey(), entry.getValue()));
            }
            else if(entry.getValue() > 0) {
                maxHeap.add(new UserAmountDTO(entry.getKey(), entry.getValue()));
            }
            else {
                System.out.println("User doesn't need to participate in settle up");
            }
        }
        List<SettlementTransaction> settlementTransactions = new ArrayList<>();
        while(!maxHeap.isEmpty() && !minHeap.isEmpty()) {
            UserAmountDTO borrower = minHeap.poll();
            UserAmountDTO lender = maxHeap.poll();

            if(Math.abs(borrower.getAmount()) > lender.getAmount()) {
                // Lender = 500, Borrower = -1000, borrower pays lender 500
                borrower.setAmount(borrower.getAmount() + lender.getAmount());
                minHeap.add(borrower);
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), lender.getAmount());
                settlementTransactions.add(settlementTransaction);
            } else if (Math.abs(borrower.getAmount()) < lender.getAmount()) {
                // Lender = 1000, Borrower = -500
                lender.setAmount(lender.getAmount() - borrower.getAmount());
                maxHeap.add(lender);
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), Math.abs(borrower.getAmount()));
                settlementTransactions.add(settlementTransaction);
            } else { // Math.abs(borrower.getAmount()) == lender.getAmount()
                // Lender = 500, Borrower = -500
                // Transaction -> Borrower to Lender 500, and both are free from settle up
                System.out.println("Do nothing, both are equal");
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), lender.getAmount());
                settlementTransactions.add(settlementTransaction);
            }
        }
        return settlementTransactions;
    }

    private HashMap<User, Double> getOutstandingBalances(List<Expense> expenses) {
        HashMap<User, Double> expenseMap = new HashMap<>();
        for (Expense expense : expenses) {
            for(UserExpense userExpense : expense.getUserExpenses()) {
                User participant = userExpense.getUser();
                double amount = userExpense.getAmount();
                if (expenseMap.containsKey(participant)) {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                        expenseMap.put(participant, expenseMap.get(participant) + amount);
                    }
                    else {
                        expenseMap.put(participant, expenseMap.get(participant) - amount);
                    }
                }
                else {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                        expenseMap.put(participant, 0 + amount);
                    }
                    else {
                        expenseMap.put(participant, 0 - amount);
                    }
                }
            }
        }
        return expenseMap;
    }
}
/*
    1. Go through all the expenses, and find the total outstanding for each person
    2. All borrowers will go to a min heap
    3. All lenders will go to a max heap
    4. Pull min from minHeap & max from maxHeap, and create a transaction
    5. Update the balances , put them back in heap
    6. Keep doing until heap is empty

    Lender = 500, Borrower = -500
    make both of them zero and keep both of them out of heap

    Lender = 1000, Borrower = -500
    Borrower will become zero, lender will become 500 and lender will go inside the heap again

    Lender = 500, Borrower = -1000
    Lender will become zero, Borrower will become -500 and lender will go inside the heap again
 */
