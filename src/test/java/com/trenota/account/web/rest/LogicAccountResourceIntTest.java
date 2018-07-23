package com.trenota.account.web.rest;

import com.trenota.account.AccountApp;

import com.trenota.account.domain.LogicAccount;
import com.trenota.account.repository.LogicAccountRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.trenota.account.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LogicAccountResource REST controller.
 *
 * @see LogicAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApp.class)
public class LogicAccountResourceIntTest {

    private static final String DEFAULT_CODICE_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CELLULARE = "AAAAAAAAAA";
    private static final String UPDATED_CELLULARE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private LogicAccountRepository logicAccountRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogicAccountMockMvc;

    private LogicAccount logicAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogicAccountResource logicAccountResource = new LogicAccountResource(logicAccountRepository);
        this.restLogicAccountMockMvc = MockMvcBuilders.standaloneSetup(logicAccountResource)
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
    public static LogicAccount createEntity(EntityManager em) {
        LogicAccount logicAccount = new LogicAccount()
            .codiceFiscale(DEFAULT_CODICE_FISCALE)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .cellulare(DEFAULT_CELLULARE)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return logicAccount;
    }

    @Before
    public void initTest() {
        logicAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogicAccount() throws Exception {
        int databaseSizeBeforeCreate = logicAccountRepository.findAll().size();

        // Create the LogicAccount
        restLogicAccountMockMvc.perform(post("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logicAccount)))
            .andExpect(status().isCreated());

        // Validate the LogicAccount in the database
        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeCreate + 1);
        LogicAccount testLogicAccount = logicAccountList.get(logicAccountList.size() - 1);
        assertThat(testLogicAccount.getCodiceFiscale()).isEqualTo(DEFAULT_CODICE_FISCALE);
        assertThat(testLogicAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLogicAccount.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testLogicAccount.getCellulare()).isEqualTo(DEFAULT_CELLULARE);
        assertThat(testLogicAccount.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testLogicAccount.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLogicAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logicAccountRepository.findAll().size();

        // Create the LogicAccount with an existing ID
        logicAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogicAccountMockMvc.perform(post("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logicAccount)))
            .andExpect(status().isBadRequest());

        // Validate the LogicAccount in the database
        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceFiscaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicAccountRepository.findAll().size();
        // set the field null
        logicAccount.setCodiceFiscale(null);

        // Create the LogicAccount, which fails.

        restLogicAccountMockMvc.perform(post("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logicAccount)))
            .andExpect(status().isBadRequest());

        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicAccountRepository.findAll().size();
        // set the field null
        logicAccount.setEmail(null);

        // Create the LogicAccount, which fails.

        restLogicAccountMockMvc.perform(post("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logicAccount)))
            .andExpect(status().isBadRequest());

        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogicAccounts() throws Exception {
        // Initialize the database
        logicAccountRepository.saveAndFlush(logicAccount);

        // Get all the logicAccountList
        restLogicAccountMockMvc.perform(get("/api/logic-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logicAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].cellulare").value(hasItem(DEFAULT_CELLULARE.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }
    

    @Test
    @Transactional
    public void getLogicAccount() throws Exception {
        // Initialize the database
        logicAccountRepository.saveAndFlush(logicAccount);

        // Get the logicAccount
        restLogicAccountMockMvc.perform(get("/api/logic-accounts/{id}", logicAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logicAccount.getId().intValue()))
            .andExpect(jsonPath("$.codiceFiscale").value(DEFAULT_CODICE_FISCALE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.cellulare").value(DEFAULT_CELLULARE.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }
    @Test
    @Transactional
    public void getNonExistingLogicAccount() throws Exception {
        // Get the logicAccount
        restLogicAccountMockMvc.perform(get("/api/logic-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogicAccount() throws Exception {
        // Initialize the database
        logicAccountRepository.saveAndFlush(logicAccount);

        int databaseSizeBeforeUpdate = logicAccountRepository.findAll().size();

        // Update the logicAccount
        LogicAccount updatedLogicAccount = logicAccountRepository.findById(logicAccount.getId()).get();
        // Disconnect from session so that the updates on updatedLogicAccount are not directly saved in db
        em.detach(updatedLogicAccount);
        updatedLogicAccount
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .cellulare(UPDATED_CELLULARE)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restLogicAccountMockMvc.perform(put("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogicAccount)))
            .andExpect(status().isOk());

        // Validate the LogicAccount in the database
        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeUpdate);
        LogicAccount testLogicAccount = logicAccountList.get(logicAccountList.size() - 1);
        assertThat(testLogicAccount.getCodiceFiscale()).isEqualTo(UPDATED_CODICE_FISCALE);
        assertThat(testLogicAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLogicAccount.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testLogicAccount.getCellulare()).isEqualTo(UPDATED_CELLULARE);
        assertThat(testLogicAccount.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testLogicAccount.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLogicAccount() throws Exception {
        int databaseSizeBeforeUpdate = logicAccountRepository.findAll().size();

        // Create the LogicAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogicAccountMockMvc.perform(put("/api/logic-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logicAccount)))
            .andExpect(status().isBadRequest());

        // Validate the LogicAccount in the database
        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogicAccount() throws Exception {
        // Initialize the database
        logicAccountRepository.saveAndFlush(logicAccount);

        int databaseSizeBeforeDelete = logicAccountRepository.findAll().size();

        // Get the logicAccount
        restLogicAccountMockMvc.perform(delete("/api/logic-accounts/{id}", logicAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LogicAccount> logicAccountList = logicAccountRepository.findAll();
        assertThat(logicAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogicAccount.class);
        LogicAccount logicAccount1 = new LogicAccount();
        logicAccount1.setId(1L);
        LogicAccount logicAccount2 = new LogicAccount();
        logicAccount2.setId(logicAccount1.getId());
        assertThat(logicAccount1).isEqualTo(logicAccount2);
        logicAccount2.setId(2L);
        assertThat(logicAccount1).isNotEqualTo(logicAccount2);
        logicAccount1.setId(null);
        assertThat(logicAccount1).isNotEqualTo(logicAccount2);
    }
}
