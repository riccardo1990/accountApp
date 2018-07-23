package com.trenota.account.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.trenota.account.domain.Affiliazione;
import com.trenota.account.repository.AffiliazioneRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Affiliazione.
 */
@RestController
@RequestMapping("/api")
public class AffiliazioneResource {

    private final Logger log = LoggerFactory.getLogger(AffiliazioneResource.class);

    private static final String ENTITY_NAME = "affiliazione";

    private final AffiliazioneRepository affiliazioneRepository;

    public AffiliazioneResource(AffiliazioneRepository affiliazioneRepository) {
        this.affiliazioneRepository = affiliazioneRepository;
    }

    /**
     * POST  /affiliaziones : Create a new affiliazione.
     *
     * @param affiliazione the affiliazione to create
     * @return the ResponseEntity with status 201 (Created) and with body the new affiliazione, or with status 400 (Bad Request) if the affiliazione has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/affiliaziones")
    @Timed
    public ResponseEntity<Affiliazione> createAffiliazione(@RequestBody Affiliazione affiliazione) throws URISyntaxException {
        log.debug("REST request to save Affiliazione : {}", affiliazione);
        if (affiliazione.getId() != null) {
            throw new BadRequestAlertException("A new affiliazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affiliazione result = affiliazioneRepository.save(affiliazione);
        return ResponseEntity.created(new URI("/api/affiliaziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /affiliaziones : Updates an existing affiliazione.
     *
     * @param affiliazione the affiliazione to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated affiliazione,
     * or with status 400 (Bad Request) if the affiliazione is not valid,
     * or with status 500 (Internal Server Error) if the affiliazione couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/affiliaziones")
    @Timed
    public ResponseEntity<Affiliazione> updateAffiliazione(@RequestBody Affiliazione affiliazione) throws URISyntaxException {
        log.debug("REST request to update Affiliazione : {}", affiliazione);
        if (affiliazione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Affiliazione result = affiliazioneRepository.save(affiliazione);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, affiliazione.getId().toString()))
            .body(result);
    }

    /**
     * GET  /affiliaziones : get all the affiliaziones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of affiliaziones in body
     */
    @GetMapping("/affiliaziones")
    @Timed
    public ResponseEntity<List<Affiliazione>> getAllAffiliaziones(Pageable pageable) {
        log.debug("REST request to get a page of Affiliaziones");
        Page<Affiliazione> page = affiliazioneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/affiliaziones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /affiliaziones/:id : get the "id" affiliazione.
     *
     * @param id the id of the affiliazione to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the affiliazione, or with status 404 (Not Found)
     */
    @GetMapping("/affiliaziones/{id}")
    @Timed
    public ResponseEntity<Affiliazione> getAffiliazione(@PathVariable Long id) {
        log.debug("REST request to get Affiliazione : {}", id);
        Optional<Affiliazione> affiliazione = affiliazioneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(affiliazione);
    }

    /**
     * DELETE  /affiliaziones/:id : delete the "id" affiliazione.
     *
     * @param id the id of the affiliazione to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/affiliaziones/{id}")
    @Timed
    public ResponseEntity<Void> deleteAffiliazione(@PathVariable Long id) {
        log.debug("REST request to delete Affiliazione : {}", id);

        affiliazioneRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
