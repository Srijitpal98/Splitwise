package dev.srijit.Splitwise.service.strategy.settleUpStrategy;

import dev.srijit.Splitwise.entity.Expense;
import dev.srijit.Splitwise.entity.SettlementTransaction;

import java.util.List;

public interface SettleUpStrategy {
    List<SettlementTransaction> getSettlementTransactions(List<Expense> expenses);
}
