package com.MindHub.homebanking;

import com.MindHub.homebanking.models.*;
import com.MindHub.homebanking.repositories.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.MindHub.homebanking.models.CardColor.*;
import static com.MindHub.homebanking.models.CardType.CREDIT;
import static com.MindHub.homebanking.models.CardType.DEBIT;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Loan hipotecario = new Loan("Mortgage", 500000, List.of(12,24,36,48,60), 24f);
			loanRepository.save(hipotecario);
			Loan personal = new Loan("Personal", 100000, List.of(6,12,24), 9f);
			loanRepository.save(personal);
			Loan automotriz = new Loan("Car", 300000, List.of(6,12,24,36), 12f);
			loanRepository.save(automotriz);



			// save a couple of customers
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123456"));
			clientRepository.save(client1);
			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000, client1, false, AccountType.SAVING);
			accountRepository.save(account1);
			Transaction transaction1 = new Transaction(LocalDateTime.now(), 2000, "Impuestos", TransactionType.DEBIT, account1, account1.getBalance());
			transactionRepository.save(transaction1);
//			Transaction transaction2 = new Transaction(LocalDateTime.now(), 2000, "Transferencia", TransactionType.CREDIT, account1);
//			transactionRepository.save(transaction2);
//			Transaction transaction7 = new Transaction(LocalDateTime.now(), 350, "Spotify", TransactionType.CREDIT, account1);
//			transactionRepository.save(transaction7);
//			Transaction transaction8 = new Transaction(LocalDateTime.now(), 560, "Netflix", TransactionType.CREDIT, account1);
//			transactionRepository.save(transaction8);
			Account account2 = new Account("VIN002", LocalDateTime.now(), 7500, client1, false, AccountType.CURRENT);
			accountRepository.save(account2);
			ClientLoan loan1 = new ClientLoan(400000, 60, client1, hipotecario);
			clientLoanRepository.save(loan1);
			ClientLoan loan2 = new ClientLoan(50000, 12, client1, personal);
			clientLoanRepository.save(loan2);
			Card card1 = new Card(GOLD, DEBIT, "5135 - 8945 - 6584 - 1548", 384, LocalDate.now(), LocalDate.now().plusYears(5), client1, false);
			cardRepository.save(card1);
			Card card2 = new Card(TITANIUM, CREDIT, "1684 - 1548 - 8991 - 6874", 541, LocalDate.now(), LocalDate.now().plusYears(5), client1, false);
			cardRepository.save(card2);
			Card card4 = new Card(SILVER, CREDIT, "2368 - 4578 - 5698 - 5874", 286, LocalDate.now(), LocalDate.now().plusYears(5), client1, false);
			cardRepository.save(card4);

			Client client2 = new Client("Adrian", "Suar", "suara@mindhub.com", passwordEncoder.encode("987654"));
			clientRepository.save(client2);
			Account account3 = new Account("VIN003", LocalDateTime.now(), 10000, client2, false, AccountType.SAVING);
			accountRepository.save(account3);
//			Transaction transaction3 = new Transaction(LocalDateTime.now(), 4000, "Supermercado", TransactionType.DEBIT, account3);
//			transactionRepository.save(transaction3);
//			Transaction transaction4 = new Transaction(LocalDateTime.now(), 2000, "Servicios", TransactionType.DEBIT, account3);
//			transactionRepository.save(transaction4);
			Account account4 = new Account("VIN004", LocalDateTime.now(), 2000, client2, false, AccountType.CURRENT);
			accountRepository.save(account4);



			Client client3 = new Client("Andres", "Garcia", "andres@mindhub.com", passwordEncoder.encode("147852369"));
			clientRepository.save(client3);
			Account account5 = new Account("VIN005", LocalDateTime.now(), 15000, client3, false, AccountType.SAVING);
			accountRepository.save(account5);
//			Transaction transaction5 = new Transaction(LocalDateTime.now(), 5000, "Impuestos", TransactionType.DEBIT, account5);
//			transactionRepository.save(transaction5);
//			Transaction transaction6 = new Transaction(LocalDateTime.now(), 2000, "Transferencia", TransactionType.CREDIT, account5);
//			transactionRepository.save(transaction6);
			Account account6 = new Account("VIN006", LocalDateTime.now(), 3000, client3, false, AccountType.CURRENT);
			accountRepository.save(account6);
			ClientLoan loan3 = new ClientLoan(100000, 12, client3, personal);
			clientLoanRepository.save(loan3);
			ClientLoan loan4 = new ClientLoan(200000, 36, client3, automotriz);
			clientLoanRepository.save(loan4);
			Card card3 = new Card(SILVER, CREDIT, "8465 - 4862 - 8851 - 5548", 984, LocalDate.now(), LocalDate.now().plusYears(5), client3, false);
			cardRepository.save(card3);



			Client admin = new Client ("admin", "admin", "admin@mindhub.com", passwordEncoder.encode("admin"));
			clientRepository.save(admin);
		};
	}
}


