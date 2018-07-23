package com.trenota.account.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.trenota.account.domain.LogicAccount;
import com.trenota.account.repository.LogicAccountRepository;
import com.trenota.account.web.rest.errors.BadRequestAlertException;
import com.trenota.account.web.rest.util.HeaderUtil;
import com.trenota.account.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LogicAccount.
 */
@RestController
@RequestMapping("/api")
public class LogicAccountResource {

    private final Logger log = LoggerFactory.getLogger(LogicAccountResource.class);

    private static final String ENTITY_NAME = "logicAccount";

    private final LogicAccountRepository logicAccountRepository;

    public LogicAccountResource(LogicAccountRepository logicAccountRepository) {
        this.logicAccountRepository = logicAccountRepository;
    }

    /**
     * POST  /logic-accounts : Create a new logicAccount.
     *
     * @param logicAccount the logicAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logicAccount, or with status 400 (Bad Request) if the logicAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logic-accounts")
    @Timed
    public ResponseEntity<LogicAccount> createLogicAccount(@Valid @RequestBody LogicAccount logicAccount) throws URISyntaxException {
        log.debug("REST request to save LogicAccount : {}", logicAccount);
        if (logicAccount.getId() != null) {
            throw new BadRequestAlertException("A new logicAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogicAccount result = logicAccountRepository.save(logicAccount);
        return ResponseEntity.created(new URI("/api/logic-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logic-accounts : Updates an existing logicAccount.
     *
     * @param logicAccount the logicAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logicAccount,
     * or with status 400 (Bad Request) if the logicAccount is not valid,
     * or with status 500 (Internal Server Error) if the logicAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logic-accounts")
    @Timed
    public ResponseEntity<LogicAccount> updateLogicAccount(@Valid @RequestBody LogicAccount logicAccount) throws URISyntaxException {
        log.debug("REST request to update LogicAccount : {}", logicAccount);
        if (logicAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogicAccount result = logicAccountRepository.save(logicAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logicAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logic-accounts : get all the logicAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logicAccounts in body
     */
    @GetMapping("/logic-accounts")
    @Timed
    public ResponseEntity<List<LogicAccount>> getAllLogicAccounts(Pageable pageable) {
        log.debug("REST request to get a page of LogicAccounts");
        Page<LogicAccount> page = logicAccountRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logic-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /logic-accounts/:id : get the "id" logicAccount.
     *
     * @param id the id of the logicAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logicAccount, or with status 404 (Not Found)
     */
    @GetMapping("/logic-accounts/{id}")
    @Timed
    public ResponseEntity<LogicAccount> getLogicAccount(@PathVariable Long id) {
        log.debug("REST request to get LogicAccount : {}", id);
        Optional<LogicAccount> logicAccount = logicAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(logicAccount);
    }

    /**
     * DELETE  /logic-accounts/:id : delete the "id" logicAccount.
     *
     * @param id the id of the logicAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logic-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogicAccount(@PathVariable Long id) {
        log.debug("REST request to delete LogicAccount : {}", id);

        logicAccountRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
