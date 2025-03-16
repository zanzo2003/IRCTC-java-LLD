package net.shashwathbhaskar.services;

import net.shashwathbhaskar.models.Ticket;

public class TicketService {

    public static void getTicketInfo(Ticket ticket){
        System.out.println("Booking Id : " + ticket.getUuid());
        System.out.println("Boarding : " + ticket.getSource());
        System.out.println("Destination : " + ticket.getDestination());
        System.out.println("Train No : " + ticket.getTrain().getTrainNo());
    }
}
