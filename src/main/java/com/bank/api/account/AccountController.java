package com.bank.api.account;

import com.bank.api.account.service.AccountHistoryListService;
import com.bank.api.account.service.AccountService;
import com.bank.api.customer.exception.CustomerNotFound;
import com.bank.api.customer.exception.CustomerNotHaveEnoughMoney;
import com.bank.api.customer.pojo.UserInputTransactionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/account/", produces = "application/json")
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountHistoryListService accountHistoryListService;


	@PostMapping("/transaction")
	public ResponseEntity transaction(@RequestBody UserInputTransactionValue userInputTransactionValue) {
		try {
			return ResponseEntity.ok(accountService.makeTransaction(userInputTransactionValue));
		} catch (CustomerNotHaveEnoughMoney | CustomerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/{id}/info")
	public ResponseEntity getUserAccount(@PathVariable long id) {
		try {
			return ResponseEntity.ok(accountService.getUserAccount(id));
		} catch (CustomerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/{id}/history")
	public ResponseEntity getAccountHistory(@PathVariable long id) {
		try {
			return ResponseEntity.ok(accountHistoryListService.getAccountHistory(id));
		} catch (CustomerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

}
