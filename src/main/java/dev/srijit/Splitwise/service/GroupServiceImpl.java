package dev.srijit.Splitwise.service;

import dev.srijit.Splitwise.entity.SettlementTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Override
    public List<SettlementTransaction> settleUp(int groupId) {
        return null;
    }
}
