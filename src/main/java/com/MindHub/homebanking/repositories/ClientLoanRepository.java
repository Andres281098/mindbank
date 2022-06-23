package com.MindHub.homebanking.repositories;

import com.MindHub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}
