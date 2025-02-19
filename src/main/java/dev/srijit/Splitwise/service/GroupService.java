package dev.srijit.Splitwise.service;

import dev.srijit.Splitwise.entity.SettlementTransaction;

import java.util.List;

public interface GroupService {
    List<SettlementTransaction> settleUp(int groupId);
}
