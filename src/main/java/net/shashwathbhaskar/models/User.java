package net.shashwathbhaskar.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private String username;

    private String password;

    private String hashPassword;

    private List<Ticket> tickets;

    private String uuid;
}
