package com.ereceipt.CAZAEORPROJECT.ERECEIPT_CONTROLLER.EOR;


import com.ereceipt.CAZAEORPROJECT.ERECEIPT_DTO.EOR.TRANSACTION;
import com.ereceipt.CAZAEORPROJECT.ERECEIPT_SERVICE.EOR.TRANSACTION_SERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eor")
public class TRANSACTION_CONTROLLER_CRUD_SAMPLE {

    TRANSACTION_SERVICE transactionService;

@Autowired
    public TRANSACTION_CONTROLLER_CRUD_SAMPLE(TRANSACTION_SERVICE transactionService){
    this.transactionService = transactionService;

    }
    @GetMapping("/transactions")
    public List<TRANSACTION> getALL() {

        return this.transactionService.getAll();

    }

    @PostMapping(value = "/transactions", consumes = "application/json")

    public TRANSACTION save(@RequestBody TRANSACTION transaction ) {
        return this.transactionService.add(transaction);

    }
    @DeleteMapping(value = "/transactions/{id}") //DELETE METHOD
    public void delete(@PathVariable Integer id) {
        this.transactionService.delete(id);

    }
    @PutMapping(value = "/transactions/{id}") //PUT(UPDATE) METHOD.
    public ResponseEntity<String> updateData(@PathVariable Integer id,
                                             @RequestBody
                                             TRANSACTION updatedDepartment) {
        return this.transactionService.update(id, updatedDepartment);
    }

}
