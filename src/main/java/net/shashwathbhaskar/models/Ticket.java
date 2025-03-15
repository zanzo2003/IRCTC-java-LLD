package net.shashwathbhaskar.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Ticket {

    private String uuid;

    private String userId;

    private String source;

    private String destination;

    private Date dateofTravel;

    private Train train;
}
