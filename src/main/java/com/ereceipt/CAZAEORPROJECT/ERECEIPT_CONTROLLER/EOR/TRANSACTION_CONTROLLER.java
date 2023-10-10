package com.ereceipt.CAZAEORPROJECT.ERECEIPT_CONTROLLER.EOR;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_DTO.EOR.TRANSACTION;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_EMAIL_IMPLEMENTATION.EOR.EMAIL_SERVICE_IMPL;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_PDF_GENERATOR.EOR.PdfGeneratorService;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_SERVICE.EOR.TRANSACTION_SERVICE;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/eor")
public class TRANSACTION_CONTROLLER {

    private final TRANSACTION_SERVICE service;
    private final PdfGeneratorService pdfGeneratorService;
    private final EMAIL_SERVICE_IMPL emailServiceImpl;

    @Autowired
    public TRANSACTION_CONTROLLER(TRANSACTION_SERVICE transactionService, PdfGeneratorService pdfGeneratorService, EMAIL_SERVICE_IMPL emailServiceImpl) {
        this.service = transactionService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.emailServiceImpl = emailServiceImpl;
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity<String> add(@RequestBody TRANSACTION transaction) throws IOException {
        try {
            TRANSACTION addTransaction = service.add(transaction);
            ByteArrayOutputStream pdfBytes = pdfGeneratorService.generatePdf(transaction);
            pdfGeneratorService.generateSavePdf(transaction, pdfBytes);
            return ResponseEntity.status(HttpStatus.OK).body("DATA ADDED" + "\n" + service.add(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND" + e.getMessage());
        }

    }
    @PostMapping(value = "transactions/send-email")
    // POST (SAVE) THEN GENERATE PDF THEN AND SEND TO EMAIL IF THE EMAIL IS EXISTING ENDPOINT
    public ResponseEntity<String> addTransaction(@RequestBody TRANSACTION transaction) throws IOException {
        TRANSACTION addTransaction = service.add(transaction);
        ByteArrayOutputStream pdfBytes = pdfGeneratorService.generatePdf(transaction);
        pdfGeneratorService.generateSavePdf(transaction, pdfBytes);
        try {
            String recipientEmail = addTransaction.getCus_email(); // VARIABLE FOR THE EMAIL
            if (recipientEmail != null && !recipientEmail.isEmpty()) {
                String subject = "NEW EMAIL";
                String body = "Dear " + transaction.getNames()
                        + ",\n"
                        + "This confirms that your Debit to account transaction is succesfull.\n"
                        + "Details are as follows:\n" + "OR no. :"
                        + transaction.getOr_number() + "\n" + "Date and Time: "
                        + transaction.getDates() + "\n" + "Recipient Account: "
                        + transaction.getMode_payment() + "\n" + "Amount: "
                        + transaction.getAmount() + "\n"
                        + "Below is the attached receipt of the transaction. Thankyou!";

                emailServiceImpl.sendEmailAttachmentMessage(recipientEmail, subject, body, pdfBytes.toByteArray(), "Transaction.pdf");
                return ResponseEntity.status(HttpStatus.CREATED).body("Email sent Succesfully " + service.add(transaction));
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Recipient email empty, added succesfully");
            } // end of (try) code block

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("failed to add " + e.getMessage());
        } // end of (catch) code block
    }
    @GetMapping(value = "transactions/{id}") //GET BY ID THEN GENERATE THE DATA ENDPOINT
    @ResponseBody
    @CrossOrigin(origins = "*")

    public ResponseEntity<String> getById(@PathVariable TRANSACTION id) throws IOException {
        try {
            TRANSACTION getById = service.getBYID(id.getId());
            ByteArrayOutputStream pdfBytes;
            pdfBytes = pdfGeneratorService.generatePdf(getById);
            pdfGeneratorService.generateSavePdf(id, pdfBytes);
            return ResponseEntity.status(HttpStatus.CREATED).body("ID " + id.getId()
                    + " "
                    + "Generated thru PDF "
                    + "\n"
                    + service.getBYID(id.getId()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() );
        }
    }
}