package org.mudit.tambola.generator;

import java.io.IOException;
import java.util.List;

import org.mudit.tambola.core.TicketGenerator;
import org.mudit.tambola.core.model.Ticket;

import com.itextpdf.text.DocumentException;

public class TicketExporter {

	public void generatorAndExportTickets(int totalTickets, int ticketsPerPage, String exportDir) {
		TicketGenerator ticketGenerator = new TicketGenerator();
		List<Ticket> tickets = ticketGenerator.generateTickets(totalTickets);
		exportTickets(tickets, ticketsPerPage, exportDir);
	}
	
	public void exportTickets(List<Ticket> tickets, int ticketsPerPage, String exportDir) {
		
		// Add more logic to make sure totalTicket % ticketsPerPage == 0
		int numberOfPages = tickets.size() / ticketsPerPage;
		PDFGenerator pdfGenerator = new PDFGenerator();
		for(int page = 0; page < numberOfPages; page++) {
			int index = page * ticketsPerPage;
			try {
				
				pdfGenerator.createPdf(tickets.subList(index, index + ticketsPerPage), exportDir + "Ticket-" + (page + 1) + ".pdf");
				
			} catch (IOException | DocumentException e) {
				
				System.err.println("Could not generate PDF for Page " + page + "Reason: " + e.getMessage()); 
			}
		}
		
	}


	public static void main(String[] args) {
		TicketExporter exporter = new TicketExporter();
		exporter.generatorAndExportTickets(110, 2, "C:\\Users\\mudit\\Documents\\Housie\\Tambola-Tickets-Set-2\\");
	}

}
