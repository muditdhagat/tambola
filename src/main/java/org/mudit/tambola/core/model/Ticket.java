package org.mudit.tambola.core.model;

import java.util.Objects;

public class Ticket {

	private String id;
	private String group;
	private Integer data[][];

	public Ticket() {
		data = new Integer[3][9];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer[][] getData() {
		return data;
	}

	public void setData(Integer[][] data) {
		this.data = data;
	}
	
	public int getRowCount(int row) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (Objects.nonNull(data[row][i]))
				count++;
		}

		return count;
	}

	public int getColCount(int col) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			if (Objects.nonNull(data[i][col]))
				count++;
		}

		return count;
	}
	
	public String toPrettyString() {
		StringBuffer ticket = new StringBuffer();
		ticket.append("----------------------------------------------").append(System.lineSeparator());
		for( int row = 0; row < 3; row++) {
			ticket.append("| ");
			for ( int column = 0; column < 9; column ++) {
				if(Objects.nonNull(data[row][column])) 
					ticket.append(data[row][column] < 9 ? " " + data[row][column] : data[row][column]);
				else 
					ticket.append("  ");
				
				ticket.append(" | ");
			}
			
			ticket.append(System.lineSeparator()).append("----------------------------------------------").append(System.lineSeparator());
		}
		ticket.append(System.lineSeparator());
		return ticket.toString();
	}

	public String getTicketDetails() {
		StringBuffer ticket = new StringBuffer();
		for( int row = 0; row < 3; row++) {
			ticket.append("Row: " + row).append(" ").append(getRowCount(row)).append(System.lineSeparator());
		}
		for ( int column = 0; column < 9; column ++) {
			ticket.append("Column: " + column).append(" ").append(getColCount(column)).append(System.lineSeparator());
		}

		ticket.append(System.lineSeparator());
		return ticket.toString();
	}
		
}
