package com.ereceipt.CAZAEORPROJECT.ERECEIPT_SERVICE.EOR;

import com.ereceipt.CAZAEORPROJECT.ERECEIPT_DTO.EOR.TRANSACTION;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_DTO.EOR.TRANSACTION_REPOSITORY;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TRANSACTION_SERVICE {
    TRANSACTION_REPOSITORY transRepository;

    public TRANSACTION_SERVICE(TRANSACTION_REPOSITORY transRepository) {
        this.transRepository = transRepository;
    }

    public List<TRANSACTION> getAll() {
        return this.transRepository.findAll();
    }

    public TRANSACTION getBYID(Integer id) {
        return this.transRepository.findById(id).get();
    }

    public TRANSACTION add(TRANSACTION transaction) {
        return this.transRepository.save(transaction);
    }

    public ResponseEntity<String> update(Integer id, TRANSACTION updateTransaction) {
        Optional<TRANSACTION> transactionOptional = this.transRepository.findById(id);

        return transactionOptional.map(transaction -> {

            transaction.setOr_number(updateTransaction.getOr_number());
            transaction.setNames(updateTransaction.getNames());
            transaction.setCus_email(updateTransaction.getCus_email());
            transaction.setDates(updateTransaction.getDates());
            transaction.setAmount(updateTransaction.getAmount());
            transaction.setCustomer_no(updateTransaction.getCustomer_no());

            TRANSACTION updated = this.transRepository.save(transaction);
            return ResponseEntity.ok("ID" + id + "UPDATED SUCCESFULLY");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(""));

    }
        public void delete (Integer id) {
            this.transRepository.deleteById(id);
        }
}
