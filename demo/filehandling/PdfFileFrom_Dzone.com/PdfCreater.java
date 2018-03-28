package com.test.pdf;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
/**
 * This is to create a PDF file.
 */
public class PDFCreator {
    private final static String[] HEADER_ARRAY = {"S.No.", "CompanyName", "Income", "Year"};
    public final static Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    public final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    public static void addMetaData(Document document, String sqlXMLFileName) {
        document.addTitle("Sample Report");
        document.addSubject("Using iText");
        document.addAuthor("Arun");
    }
    public static void addContent(Document document, List<DataObject> dataObjList) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(NORMAL_FONT);
        createReportTable(paragraph, dataObjList);
        document.add(paragraph);
    }
    private static void createReportTable(Paragraph paragraph, List<DataObject> dataObjList)
    throws BadElementException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        paragraph.add(new Chunk("Report Table :- ", SMALL_BOLD));
        if(null == dataObjList){
            paragraph.add(new Chunk("No data to display."));
            return;
        }
        addHeaderInTable(HEADER_ARRAY, table);
        int count = 1;
        for(DataObject dataObject : dataObjList){
            addToTable(table, String.valueOf(count)+".");
            addToTable(table, dataObject.getComanyName());
            addToTable(table, dataObject.getIncome());
            addToTable(table, dataObject.getYear());
            count++;
        }
        paragraph.add(table);
    }
    /** Helper methods start here **/  
    public static void addTitlePage(Document document, String title) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 3);
        preface.add(new Phrase("Test Report: ", NORMAL_FONT));
        preface.add(new Phrase(title, PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 1);
        preface.add(new Phrase("Date: ", PDFCreator.SMALL_BOLD));
        preface.add(new Phrase(new Date().toString(), PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 1);
        preface.add(new Phrase("Report generated by: ", PDFCreator.SMALL_BOLD));
        preface.add(new Phrase("Arun", PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 2);
        preface.add(new Phrase("This is basically a sample report.", PDFCreator.NORMAL_FONT));
        document.addSubject("PDF : " + title);
        preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);
        document.newPage();
    }
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public static void addHeaderInTable(String[] headerArray, PdfPTable table){
        PdfPCell c1 = null;
        for(String header : headerArray) {
            c1 = new PdfPCell(new Phrase(header, PDFCreator.SMALL_BOLD));
            c1.setBackgroundColor(BaseColor.GREEN);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        table.setHeaderRows(1);
    }
    public static void addToTable(PdfPTable table, String data){        
        table.addCell(new Phrase(data, PDFCreator.NORMAL_FONT));
    }
    public static Paragraph getParagraph(){        
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(PDFCreator.NORMAL_FONT);
        addEmptyLine(paragraph, 1);
        return paragraph;
    }
}
