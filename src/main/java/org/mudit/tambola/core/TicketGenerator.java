package org.mudit.tambola.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.mudit.tambola.core.model.Ticket;

public class TicketGenerator {

	public TicketGenerator() {
		
	}

	private Map<Integer, List<Integer>> initializeColumndata() {
		// Overall Number Data
		Map<Integer, List<Integer>> columns = new HashMap<>();
		for(int i = 0; i<9; i++) {
			List<Integer> list = new ArrayList<>();
			for(int j = 0; j<=9; j++) {
				int value = (i * 10 + j);
				if( value != 0) list.add(value);
			}
			
			columns.put(i, list);			
		}
		// Add 90 to the data
		columns.get(8).add(90);
		
		return columns;
	}


	private Map<Integer, List<Integer>> generateDataset() {
		Map<Integer, List<Integer>> dataSet = new HashMap<>();
		for(int i = 0; i<9; i++) {
			List<Integer> columnData = new ArrayList<>();
			dataSet.put(i, columnData);
		}

		return dataSet;
	}
	
	private Map<Integer, Map<Integer, List<Integer>>> initializeDatasets() {
		// Init the dataSet 
		Map<Integer, Map<Integer, List<Integer>>> dataSets = new HashMap<>();
		for(int i = 0; i<6; i++) {
			dataSets.put(i, generateDataset());
		}
		
		return dataSets;
	}

	private List<Ticket> generateTicketSet() {
		Map<Integer, Map<Integer, List<Integer>>> datasets = initializeDatasets();
		// Create the datasets
		createDatasets(initializeColumndata(), datasets);
		// Sort the datasets
		sortDatasets(datasets);
		// Create the tickets from datasets
		return createTickets(datasets);
	}
	
	public List<Ticket> generateTickets(Integer numberOfTickets) {
		List<Ticket> generatedTickets = new ArrayList<>();
		while(generatedTickets.size() < numberOfTickets) {
			List<Ticket> ticketSet = generateTicketSet();
			if(numberOfTickets - generatedTickets.size() > 6) {
				generatedTickets.addAll(ticketSet);
				
			} else {
				int endIndex = numberOfTickets - generatedTickets.size();
				generatedTickets.addAll(ticketSet.subList(0, endIndex));
			}
		}
		
		int id = 1;
		for(Ticket ticket : generatedTickets) {
			ticket.setId("" + id++);
		}
		
		return generatedTickets;
	}

	private List<Ticket> createTickets(Map<Integer, Map<Integer, List<Integer>>> datasets) {
		List<Ticket> ticketSet = new ArrayList<>();
		// Iterate through the sets to get the tickets 
		String groupId = RandomStringUtils.randomNumeric(8);
		for (Map<Integer, List<Integer>> dataset : datasets.values()) {
			
			Ticket ticket = new Ticket();
			// Add some meta info to the ticket 
			ticket.setGroup(groupId);
			ticketSet.add(ticket);
			
			// First Fill All the Columns with 3 Numbers in the Column 
//			for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
//				List<Integer> columnValues = dataset.get(columnIndex);
//					if(columnValues.size() == 3) {
//						for(int row = 0; row < 3; row++) {							
//							ticket.getData()[row][columnIndex] = columnValues.remove(0);
//						}
//					}
//			}
			
			for(int row = 0; row < 3; row++) {
				List<Integer> columnUsed = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
				for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
					List<Integer> columnValues = dataset.get(columnIndex);
					if(!columnValues.isEmpty() && columnValues.size() == 3 - row) {
							ticket.getData()[row][columnIndex] = columnValues.remove(0);
							columnUsed.remove(columnUsed.indexOf(columnIndex));
					}
				}
				// Random assign remaining numbers 
				while(ticket.getRowCount(row) != 5) {
					if(columnUsed.size() == 0) {
						System.out.println("It's zero");
						System.out.println(dataset.toString());
						System.out.println(ticket.toPrettyString());
					}
					int randomColumnUsedIndex = TicketHelper.generateRandomNumber(0, columnUsed.size() - 1);
					int randomColumnIndex = columnUsed.remove(randomColumnUsedIndex);
					List<Integer> columnValues = dataset.get(randomColumnIndex);
					if(!columnValues.isEmpty()) {
						ticket.getData()[row][randomColumnIndex] = columnValues.remove(0);
					}
				}

			}
			
			
		}
		
		return ticketSet;
		
	}

	private void sortDatasets(Map<Integer, Map<Integer, List<Integer>>> datasets) {
		for(Map<Integer, List<Integer>> dataset : datasets.values()) {
			for(List<Integer> columnData : dataset.values()) {
				Collections.sort(columnData);
			}

		}
	}

	private void createDatasets(Map<Integer, List<Integer>> columnData, Map<Integer, Map<Integer, List<Integer>>> datasets) {
		// Assign each dataset with an element each for each column
		for(int col=0; col<9; col++) {
			List<Integer> columnValues = columnData.get(col);

			for(int setNum = 0; setNum<6; setNum++) {
				Map<Integer, List<Integer>> dataset = datasets.get(setNum);

				int randomIndex = TicketHelper.generateRandomNumber(0, columnValues.size() - 1);
				int randomNumber = columnValues.remove(randomIndex); // Get and Remove the number from the column data

				dataset.get(col).add(randomNumber); // Add the number of the dataset
			}
		}

		// Assign the last column's number (It has an extra number) to an random set
		List<Integer> lastColumn = columnData.get(8);

		int randomIndex = TicketHelper.generateRandomNumber(0, lastColumn.size() - 1);
		int randomNumber = lastColumn.remove(randomIndex); // Get and Remove the number from the column data

		int randomSetIndex = TicketHelper.generateRandomNumber(0, datasets.size() - 1);
		datasets.get(randomSetIndex).get(8).add(randomNumber); // Get the dataset from the datasets using the random index and add the number to the last column

		// Iterate over this process 4 times for all the remaining numbers
		// Keep the column limit to 2 for 3 passes and then 3 for the last 4th pass to ensure less 3 column numbers. 
		for(int it = 0; it < 4; it++) {
			// Iterate for all columns 
			for(int columnIndex = 0; columnIndex < 9; columnIndex++) {

				List<Integer> column = columnData.get(columnIndex);
				// Make sure there are elements in the column. 
				if(column.size() != 0) {
					// Get a random index and then get a number using that index  
					randomIndex = TicketHelper.generateRandomNumber(0, column.size() - 1);
					randomNumber = column.remove(randomIndex);
					// Randomly search for a vacant data set and then set the number there. 
					boolean datasetFound = false; 
					List<Integer> setIndex = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
					while(!datasetFound && setIndex.size() != 0) {
						
						int randomCheckedDatasetsIndex = TicketHelper.generateRandomNumber(0, setIndex.size() - 1);
						randomSetIndex = setIndex.remove(randomCheckedDatasetsIndex);

						Map<Integer, List<Integer>> dataset = datasets.get(randomSetIndex);

						if(TicketHelper.getNumberOfElementsInDataset(dataset) < 15 && dataset.get(columnIndex).size() < (it == 3 ? 3 : 2)) {
							datasetFound = true;
							dataset.get(columnIndex).add(randomNumber);
						}
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {

		TicketGenerator generator = new TicketGenerator();

		List<Ticket> tickets = generator.generateTicketSet();
		for(Ticket ticket : tickets) {
			System.out.println(ticket.toPrettyString());
			System.out.println(ticket.getTicketDetails());
		}
//		System.out.println(ticket.getRowCount(1));
//		System.out.println(generator.getColumns());
//		System.out.println(generator.getDataSets());
		
//		// Fill first row of the ticket 
//		for(int row = 0; row < 3; row++) {				
//			for ( int colSize =  (3 - row); colSize > 0; colSize--) {
//				// Maximum 5 numbers in a row is allowed. 
//				if(ticket.getRowCount(row) == 5 ) break; 
//				
//				for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
//					if(ticket.getRowCount(row) == 5 ) break;
//					// If the space is empty or null 
//					if(Objects.isNull(ticket.getData()[row][columnIndex])) {
//						List<Integer> columnValues = dataset.get(columnIndex);
//						if(columnValues.size() == colSize) {
//							ticket.getData()[row][columnIndex] = columnValues.remove(0);
//						}
//					}
//				}
//			}
//		}
		

		
//		// Now we need to fill columns with 2 Numbers in the Column
//		for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
//			List<Integer> columnValues = dataset.get(columnIndex);
//			if(columnValues.size() == 2) {
//				boolean columnFilled = false; 
//				while(!columnFilled) { // Ideally at this point all the rows for the columns should be empty.	
//					int number = columnValues.remove(0);
//					int randomRowIndex = TicketHelper.generateRandomNumber(0, 1); // If can put this on either 1/2 Row only since we have two
//					if(Objects.isNull(ticket.getData()[randomRowIndex][columnIndex]) && ticket.getRowCount(randomRowIndex) < 5 ) {
//						if(randomRowIndex == 0) {
//							
//							ticket.getData()[randomRowIndex][columnIndex] = number;
//							columnFilled = true; 
//							
//						} else {
//							
//							if(Objects.isNull(ticket.getData()[2][columnIndex]) && ticket.getRowCount(2) < 5 ) {
//								ticket.getData()[1][columnIndex] = number;
//								ticket.getData()[2][columnIndex] = columnValues.remove(0);
//								columnFilled = true; 
//							}
//							
//						}
//					}
//				}
//			}
//		}
//		
//		// Now we need to fill columns with 1 Numbers in the Column
//		for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
//			List<Integer> columnValues = dataset.get(columnIndex);
//			if(columnValues.size() == 1) {
//				boolean columnFilled = false; 
//				int number = columnValues.remove(0);
//				while(!columnFilled) { // Ideally at this point all the rows for the columns should be empty.	
//					int randomRowIndex = TicketHelper.generateRandomNumber(0, 2);
//					if(Objects.isNull(ticket.getData()[randomRowIndex][columnIndex]) && ticket.getRowCount(randomRowIndex) < 5 ) {
//						ticket.getData()[randomRowIndex][columnIndex] = number;
//						columnFilled = true;
//					}
//				}
//			}
//		}
		


	}
}
