package net.shashwathbhaskar.models;


import java.sql.Time;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class Train {

    private String trainId;

    private String trainNo;

    private List<List<Boolean>> seats;

    private Map<String, Time> timings;

    private List<String> stations;
}
