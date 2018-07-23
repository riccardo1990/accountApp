package com.trenota.account.web.rest;

import com.trenota.account.AccountApp;

import com.trenota.account.domain.Affiliazione;
import com.trenota.account.repository.AffiliazioneRepository;
import com.trenota.account.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.trenota.account.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AffiliazioneResource REST controller.
 *
 * @see AffiliazioneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApp.class)
public class AffiliazioneResourceIntTest {

    private static final String DEFAULT_COD_PUNTO_VENDITA_EXT = "AAAAAAAAAA";
    private static final String UPDATED_COD_PUNTO_VENDITA_EXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_AFFILIAZIONE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_AFFILIAZIONE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AffiliazioneRepository affiliazioneRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAffiliazioneMockMvc;

    private Affiliazione affiliazione;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffiliazioneResource affiliazioneResource = new AffiliazioneResource(affiliazioneRepository);
        this.restAffiliazioneMockMvc = MockMvcBuilders.standaloneSetup(affiliazioneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliazione createEntity(EntityManager em) {
        Affiliazione affiliazione = new Affiliazione()
            .codPuntoVenditaExt(DEFAULT_COD_PUNTO_VENDITA_EXT)
            .dataAffiliazione(DEFAULT_DATA_AFFILIAZIONE);
        return affiliazione;
    }

    @Before
    public void initTest() {
        affiliazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffiliazione() throws Exception {
        int databaseSizeBeforeCreate = affiliazioneRepository.findAll().size();

        // Create the Affiliazione
        restAffiliazioneMockMvc.perform(post("/api/affiliaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliazione)))
            .andExpect(status().isCreated());

        // Validate the Affiliazione in the database
        List<Affiliazione> affiliazioneList = affiliazioneRepository.findAll();
        assertThat(affiliazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliazione testAffiliazione = affiliazioneList.get(affiliazioneList.size() - 1);
        assertThat(testAffiliazione.getCodPuntoVenditaExt()).isEqualTo(DEFAULT_COD_PUNTO_VENDITA_EXT);
        assertThat(testAffiliazione.getDataAffiliazione()).isEqualTo(DEFAULT_DATA_AFFILIAZIONE);
    }

    @Test
    @Transactional
    public void createAffiliazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affiliazioneRepository.findAll().size();

        // Create the Affiliazione with an existing ID
        affiliazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliazioneMockMvc.perform(post("/api/affiliaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliazione)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliazione in the database
        List<Affiliazione> affiliazioneList = affiliazioneRepository.findAll();
        assertThat(affiliazioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAffiliaziones() throws Exception {
        // Initialize the database
        affiliazioneRepository.saveAndFlush(affiliazione);

        // Get all the affiliazioneList
        restAffiliazioneMockMvc.perform(get("/api/affiliaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].codPuntoVenditaExt").value(hasItem(DEFAULT_COD_PUNTO_VENDITA_EXT.toString())))
            .andExpect(jsonPath("$.[*].dataAffiliazione").value(hasItem(DEFAULT_DATA_AFFILIAZIONE.toString())));
    }
    

    @Test
    @Transactional
    public void getAffiliazione() throws Exception {
        // Initialize the database
        affiliazioneRepository.saveAndFlush(affiliazione);

        // Get the affiliazione
        restAffiliazioneMockMvc.perform(get("/api/affiliaziones/{id}", affiliazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affiliazione.getId().intValue()))
            .andExpect(jsonPath("$.codPuntoVenditaExt").value(DEFAULT_COD_PUNTO_VENDITA_EXT.toString()))
            .andExpect(jsonPath("$.dataAffiliazione").value(DEFAULT_DATA_AFFILIAZIONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAffiliazione() throws Exception {
        // Get the affiliazione
        restAffiliazioneMockMvc.perform(get("/api/affiliaziones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffiliazione() throws Exception {
        // Initialize the database
        affiliazioneRepository.saveAndFlush(affiliazione);

        int databaseSizeBeforeUpdate = affiliazioneRepository.findAll().size();

        // Update the affiliazione
        Affiliazione updatedAffiliazione = affiliazioneRepository.findById(affiliazione.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliazione are not directly saved in db
        em.detach(updatedAffiliazione);
        updatedAffiliazione
            .codPuntoVenditaExt(UPDATED_COD_PUNTO_VENDITA_EXT)
            .dataAffiliazione(UPDATED_DATA_AFFILIAZIONE);

        restAffiliazioneMockMvc.perform(put("/api/affiliaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffiliazione)))
            .andExpect(status().isOk());

        // Validate the Affiliazione in the database
        List<Affiliazione> affiliazioneList = affiliazioneRepository.findAll();
        assertThat(affiliazioneList).hasSize(databaseSizeBeforeUpdate);
        Affiliazione testAffiliazione = affiliazioneList.get(affiliazioneList.size() - 1);
        assertThat(testAffiliazione.getCodPuntoVenditaExt()).isEqualTo(UPDATED_COD_PUNTO_VENDITA_EXT);
        assertThat(testAffiliazione.getDataAffiliazione()).isEqualTo(UPDATED_DATA_AFFILIAZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingAffiliazione() throws Exception {
        int databaseSizeBeforeUpdate = affiliazioneRepository.findAll().size();

        // Create the Affiliazione

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAffiliazioneMockMvc.perform(put("/api/affiliaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliazione)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliazione in the database
        List<Affiliazione> affiliazioneList = affiliazioneRepository.findAll();
        assertThat(affiliazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffiliazione() throws Exception {
        // Initialize the database
        affiliazioneRepository.saveAndFlush(affiliazione);

        int databaseSizeBeforeDelete = affiliazioneRepository.findAll().size();

        // Get the affiliazione
        restAffiliazioneMockMvc.perform(delete("/api/affiliaziones/{id}", affiliazione.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Affiliazione> affiliazioneList = affiliazioneRepository.findAll();
        assertThat(affiliazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliazione.class);
        Affiliazione affiliazione1 = new Affiliazione();
        affiliazione1.setId(1L);
        Affiliazione affiliazione2 = new Affiliazione();
        affiliazione2.setId(affiliazione1.getId());
        assertThat(affiliazione1).isEqualTo(affiliazione2);
        affiliazione2.setId(2L);
        assertThat(affiliazione1).isNotEqualTo(affiliazione2);
        affiliazione1.setId(null);
        assertThat(affiliazione1).isNotEqualTo(affiliazione2);
    }
}
