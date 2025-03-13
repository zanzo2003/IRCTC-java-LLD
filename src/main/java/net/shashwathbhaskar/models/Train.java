package net.shashwathbhaskar.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    private String trainId;

    private String trainNo;

    private List<List<Boolean>> seats;

    private Map<String, Time> timings;

    private List<String> stations;
}
