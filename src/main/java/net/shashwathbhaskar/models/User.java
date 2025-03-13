package net.shashwathbhaskar.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private String password;

    private String hashPassword;

    private List<Ticket> tickets;

    private String uuid;
}
