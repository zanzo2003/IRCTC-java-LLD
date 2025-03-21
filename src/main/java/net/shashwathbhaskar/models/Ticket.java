package net.shashwathbhaskar.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private String uuid;

    private String userId;

    private String source;

    private String destination;

    private Date dateOfTravel;

    private Train train;
}
