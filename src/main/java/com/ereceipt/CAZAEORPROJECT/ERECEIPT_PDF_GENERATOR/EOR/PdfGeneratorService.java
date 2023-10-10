package com.ereceipt.CAZAEORPROJECT.ERECEIPT_PDF_GENERATOR.EOR;

import com.ereceipt.CAZAEORPROJECT.ERECEIPT_DTO.EOR.TRANSACTION;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service

public class PdfGeneratorService {
    public void generateSavePdf(TRANSACTION transaction, ByteArrayOutputStream pdfBytes) throws IOException, DocumentException {

        String outputFolderPath = "C:\\Users\\63975\\OneDrive - CAZA TECHNOLOGY SOLUTIONS INC\\Desktop\\GeneratedP";
        Path outputFolder = Paths.get(outputFolderPath);
        if (!Files.exists(outputFolder)) {
            Files.createDirectories(outputFolder);
        }
        //DATE AND TIME INCLUDING MINUTES AND SECONDS PARA MAGING UNIQUE YUNG ID
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = "EWB-" + transaction.getCustomer_no() + "-" + currentDateTime + ".pdf";
        String filePath = outputFolderPath + File.separator + fileName;
        ByteArrayOutputStream byteArrayOutputStream = generatePdf(transaction);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        pdfBytes.writeTo(fileOutputStream);
        byteArrayOutputStream.close();
    }

    public ByteArrayOutputStream generatePdf(TRANSACTION transaction) throws IOException, DocumentException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.COURIER);
        fontTitle.setSize(18);
        Font fontParagraphlow = FontFactory.getFont(FontFactory.COURIER);
        fontParagraphlow.setSize(13);
        Paragraph[] paragraphs = new Paragraph[]{

                new Paragraph("O-F-F-I-C-I-A-L-R-E-C-E-I-P-T", fontTitle),
                new Paragraph(" "),
                new Paragraph("CAZA - CAVITE", fontTitle),
                new Paragraph("CAZA - DELAROSA", fontTitle),
                new Paragraph("NON - VAT REG. TIN", fontTitle),
                new Paragraph("OR#: " + transaction.getOr_number(), fontTitle),
                new Paragraph("------------------------------------------------", fontTitle),
                new Paragraph("Received From: " + transaction.getNames(), fontParagraphlow),
                new Paragraph("Date: " + transaction.getDates(), fontParagraphlow),
                new Paragraph("Recieve Amount: " + transaction.getAmount(), fontParagraphlow),
                new Paragraph("Mode of Payment: " + transaction.getMode_payment(), fontParagraphlow)
        };

        paragraphs[0].setAlignment(Element.ALIGN_CENTER);
        paragraphs[1].setAlignment(Element.ALIGN_RIGHT);

        for (Paragraph paragraph : paragraphs) {
            document.add(paragraph);
        }
        document.close();
        return byteArrayOutputStream;
    }
}



//        Font fontParagraph = FontFactory.getFont(FontFactory.COURIER);
//        fontParagraph.setSize(15);
//
//        Font fontParagraphlow = FontFactory.getFont(FontFactory.COURIER);
//        fontParagraphlow.setSize(13);
//
//        Paragraph paragraph1 = new Paragraph(" ", fontTitle);
//        paragraph1.setAlignment(paragraph1.ALIGN_LEFT);
//
//        Paragraph paragraph2 = new Paragraph("CAZA - CAVITE", fontTitle);
//        paragraph2.setAlignment(paragraph2.ALIGN_LEFT);
//
//        Paragraph paragraph3 = new Paragraph("CAZA - DELAROSA", fontTitle);
//        paragraph3.setAlignment(paragraph3.ALIGN_LEFT);
//
//        Paragraph paragraph4 = new Paragraph("NON - VAT REG. TIN", fontTitle);
//        paragraph4.setAlignment(paragraph4.ALIGN_LEFT);
//
//        Paragraph paragraph5 = new Paragraph("OR#: " + department.getOr_number(), fontTitle);
//        paragraph5.setAlignment(paragraph5.ALIGN_RIGHT);
//
//
//        Paragraph paragraph6 = new Paragraph("------------------------------------------------", fontTitle);
//        paragraph6.setAlignment(paragraph6.ALIGN_LEFT);
//
//
//        Paragraph paragraphlow = new Paragraph("Received From: " + department.getNames(), fontParagraphlow);
//        paragraphlow.setAlignment(paragraphlow.ALIGN_LEFT);
//
//        Paragraph paragraphlow1 = new Paragraph("Date: " + department.getDates(), fontParagraphlow);
//        paragraphlow1.setAlignment(paragraphlow1.ALIGN_LEFT);
//
//        Paragraph paragraphlow2 = new Paragraph("Recieve Amount: " + department.getAmount(), fontParagraphlow);
//        paragraphlow2.setAlignment(paragraphlow2.ALIGN_LEFT);
//
//        Paragraph paragraphlow3 = new Paragraph("Mode of Payment: " + department.getRep_acc(), fontParagraphlow);
//        paragraphlow3.setAlignment(paragraphlow3.ALIGN_LEFT);


//        document.add(paragraph);
//        document.add(paragraph1);
//
//        document.add(paragraph2);
//        document.add(paragraph5);
//        document.add(paragraph3);
//        document.add(paragraph4);
//        document.add(paragraph6);
//        document.add(paragraphlow);
//        document.add(paragraphlow1);
//        document.add(paragraphlow2);
//        document.add(paragraphlow3);

