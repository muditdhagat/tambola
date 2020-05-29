package org.mudit.tambola.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.mudit.tambola.AppConstants;
import org.mudit.tambola.core.TicketGenerator;
import org.mudit.tambola.core.model.Ticket;
import org.mudit.tambola.model.TicketSet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {

  public static void main(String[] args) throws IOException, DocumentException {

    File file = new File(AppConstants.exportDirBasePath + "test/Test-Ticket.pdf");
    file.getParentFile().mkdirs();
    TicketGenerator ticketGenerator = new TicketGenerator();
    new PDFGenerator().createPdf(ticketGenerator.generateTicketSets(2, 1).get(0), "Dhagat's Family", AppConstants.exportDirBasePath + "/test/Test-Ticket.pdf");
  }

  public void createPdf(List<Ticket> tickets, String dest) throws IOException, DocumentException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(dest));
    document.open();
    Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
    Font metaFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL);
    for (Ticket ticket : tickets) {
      Integer[][] data = ticket.getData();

      PdfPTable table = new PdfPTable(9);
      table.setWidthPercentage(80);
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.setSpacingAfter(30);

      PdfPCell topCellLeft = new PdfPCell();
      topCellLeft.setBackgroundColor(BaseColor.WHITE);
      topCellLeft.setColspan(4);
      topCellLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
      topCellLeft.disableBorderSide(Rectangle.RIGHT);
      topCellLeft.setPadding(5);
      Paragraph ticketTitle = new Paragraph("Tambola Ticket", metaFont);
      ticketTitle.setIndentationLeft(5);
      ticketTitle.setAlignment(Element.ALIGN_LEFT);
      topCellLeft.addElement(ticketTitle);
      table.addCell(topCellLeft);

      PdfPCell topCellRight = new PdfPCell();
      topCellRight.setBackgroundColor(BaseColor.WHITE);
      topCellRight.setColspan(5);
      topCellRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
      topCellRight.disableBorderSide(Rectangle.LEFT);
      topCellRight.setPadding(5);
      Paragraph ticketNumber = new Paragraph("Ticket No." + ticket.getId(), metaFont);
      ticketNumber.setIndentationRight(5);
      ticketNumber.setAlignment(Element.ALIGN_RIGHT);
      topCellRight.addElement(ticketNumber);
      table.addCell(topCellRight);

      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
          PdfPCell cell = new PdfPCell();
          cell.setMinimumHeight(50);
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          if (Objects.nonNull(data[row][col])) {
            Paragraph para = new Paragraph(String.valueOf(data[row][col]), font);
            para.setAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.addElement(para);

          } else {
            Paragraph para = new Paragraph("");
            cell.addElement(para);
          }

          table.addCell(cell);
        }
      }

      document.add(table);
    }

    document.close();
  }

  public void createPdf(TicketSet ticketSet, String name, String dest) throws IOException, DocumentException {
    Document document = new Document();
    addMetaData(document);
    PdfWriter.getInstance(document, new FileOutputStream(dest));
    document.open();
    BaseFont baseFont = BaseFont.createFont("D:\\Work\\designing\\font-collection\\Roboto-Light.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
    BaseFont baseFontBold = BaseFont.createFont("D:\\Work\\designing\\font-collection\\Roboto-Bold.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
    
    Font font = new Font(baseFontBold, 14, Font.NORMAL);
    Font metaFont = new Font(baseFont, 8, Font.NORMAL);
    Font titleFont = new Font(baseFont, 12, Font.NORMAL);
    PdfPTable titleTable = new PdfPTable(2);
    titleTable.setWidthPercentage(80);
    titleTable.setHorizontalAlignment(Element.ALIGN_CENTER);
    titleTable.setSpacingAfter(15);
    
    PdfPCell leftCellTitle = new PdfPCell();
    leftCellTitle.setBackgroundColor(BaseColor.WHITE);
    leftCellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
    leftCellTitle.setBorder(0);
    leftCellTitle.disableBorderSide(Rectangle.NO_BORDER);
    Paragraph mainTitle = new Paragraph(name + " Game", titleFont);
    mainTitle.setIndentationLeft(10);
    mainTitle.setAlignment(Element.ALIGN_LEFT);
    leftCellTitle.addElement(mainTitle);
    titleTable.addCell(leftCellTitle);
    
    PdfPCell rightCellTitle = new PdfPCell();
    rightCellTitle.setBackgroundColor(BaseColor.WHITE);
    rightCellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
    rightCellTitle.setBorder(0);
    Paragraph rightMainTitle = new Paragraph("Ticket Id: " + ticketSet.getTicketId(), titleFont);
    rightMainTitle.setAlignment(Element.ALIGN_RIGHT);
    rightMainTitle.setIndentationRight(10);
    rightCellTitle.addElement(rightMainTitle);
    titleTable.addCell(rightCellTitle);
    
    document.add(titleTable);
    
    for (Ticket ticket : ticketSet.getTickets()) {
      Integer[][] data = ticket.getData();

      PdfPTable table = new PdfPTable(9);
      table.setWidthPercentage(80);
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.setSpacingAfter(30);

      PdfPCell topCellLeft = new PdfPCell();
      topCellLeft.setMinimumHeight(10);
      topCellLeft.setBackgroundColor(BaseColor.WHITE);
      topCellLeft.setColspan(4);
      topCellLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
      topCellLeft.disableBorderSide(Rectangle.RIGHT);
      topCellLeft.setPadding(5);
      Paragraph ticketTitle = new Paragraph("Tambola Ticket", metaFont);
      ticketTitle.setIndentationLeft(5);
      ticketTitle.setAlignment(Element.ALIGN_LEFT);
      topCellLeft.addElement(ticketTitle);
      table.addCell(topCellLeft);
      
      PdfPCell topCellRight = new PdfPCell();
      topCellRight.setMinimumHeight(10);
      topCellRight.setBackgroundColor(BaseColor.WHITE);
      topCellRight.setColspan(5);
      topCellRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
      topCellRight.disableBorderSide(Rectangle.LEFT);
      topCellRight.setPadding(5);
      Paragraph ticketNumber = new Paragraph("Ticket No." + ticket.getId(), metaFont);
      ticketNumber.setIndentationRight(5);
      ticketNumber.setAlignment(Element.ALIGN_RIGHT);
      topCellRight.addElement(ticketNumber);
      table.addCell(topCellRight);

      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
          PdfPCell cell = new PdfPCell();
          cell.setMinimumHeight(50);
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          if (Objects.nonNull(data[row][col])) {
            Paragraph para = new Paragraph(String.valueOf(data[row][col]), font);
            para.setAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.addElement(para);

          } else {
            Paragraph para = new Paragraph("");
            cell.addElement(para);
          }

          table.addCell(cell);
        }
      }

      document.add(table);
    }

    document.close();
  }

private void addMetaData(Document document){
    document.addTitle("Tambola Game Ticket");
    document.addSubject("Tambola Ticket");
    document.addCreator("Tambola Ticket Generator");
    document.addAuthor("Mudit");
}
}
