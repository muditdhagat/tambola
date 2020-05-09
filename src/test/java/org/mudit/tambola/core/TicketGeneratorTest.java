package org.mudit.tambola.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mudit.tambola.core.model.Ticket;

public class TicketGeneratorTest {
	
	@Test
	public void GeneratorTest() {		
		TicketGenerator generator = new TicketGenerator();
		List<Ticket> tickets = generator.generateTickets(6);
		Assert.assertEquals(6, tickets.size());
		for(Ticket ticket : tickets) {
			System.out.println(ticket.toPrettyString());
			Assert.assertEquals(5, ticket.getRowCount(0));
			Assert.assertEquals(5, ticket.getRowCount(1));
			Assert.assertEquals(5, ticket.getRowCount(2));
		}
	}
	
	@Test
	public void GeneratorLoadTest() {
		int load = 20000;
		long start = System.currentTimeMillis();
		for(int i = 0; i < load/6; i++) {			
			TicketGenerator generator = new TicketGenerator();
			List<Ticket> tickets = generator.generateTickets(6);
			Assert.assertEquals(6, tickets.size());
			for(Ticket ticket : tickets) {
//				System.out.println(ticket.toPrettyString());
				Assert.assertEquals(5, ticket.getRowCount(0));
				Assert.assertEquals(5, ticket.getRowCount(1));
				Assert.assertEquals(5, ticket.getRowCount(2));
			}
		}
		
		System.out.println("Total time taken to generate " + load + " tickets is " + (System.currentTimeMillis() - start) + " ms");
	}
	
	@Test
	public void RandomNumberGeneratorTest() {
		Assert.assertEquals(0, TicketHelper.generateRandomNumber(0, 0));
		Assert.assertEquals(0, TicketHelper.generateRandomNumber(0, -1));
	}
	

}
