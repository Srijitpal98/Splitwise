package dev.srijit.Splitwise.dto;

import dev.srijit.Splitwise.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAmountDTO {
    private User user;
    private double amount;

    public UserAmountDTO(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }
}
