package com.yats.app.web.rest;

import com.yats.app.SnippetsApp;
import com.yats.app.domain.Snippet;
import com.yats.app.repository.SnippetRepository;
import com.yats.app.service.SnippetService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SnippetResource} REST controller.
 */
@SpringBootTest(classes = SnippetsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SnippetResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final Long DEFAULT_GITLAB_EXTERNAL_CODE = 1L;
    private static final Long UPDATED_GITLAB_EXTERNAL_CODE = 2L;

    @Autowired
    private SnippetRepository snippetRepository;

    @Autowired
    private SnippetService snippetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSnippetMockMvc;

    private Snippet snippet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Snippet createEntity(EntityManager em) {
        Snippet snippet = new Snippet()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .textCode(DEFAULT_TEXT_CODE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .actif(DEFAULT_ACTIF)
            .gitlabExternalCode(DEFAULT_GITLAB_EXTERNAL_CODE);
        return snippet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Snippet createUpdatedEntity(EntityManager em) {
        Snippet snippet = new Snippet()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .textCode(UPDATED_TEXT_CODE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .actif(UPDATED_ACTIF)
            .gitlabExternalCode(UPDATED_GITLAB_EXTERNAL_CODE);
        return snippet;
    }

    @BeforeEach
    public void initTest() {
        snippet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSnippet() throws Exception {
        int databaseSizeBeforeCreate = snippetRepository.findAll().size();
        // Create the Snippet
        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isCreated());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeCreate + 1);
        Snippet testSnippet = snippetList.get(snippetList.size() - 1);
        assertThat(testSnippet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSnippet.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSnippet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSnippet.getTextCode()).isEqualTo(DEFAULT_TEXT_CODE);
        assertThat(testSnippet.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testSnippet.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testSnippet.isActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testSnippet.getGitlabExternalCode()).isEqualTo(DEFAULT_GITLAB_EXTERNAL_CODE);
    }

    @Test
    @Transactional
    public void createSnippetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = snippetRepository.findAll().size();

        // Create the Snippet with an existing ID
        snippet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setCode(null);

        // Create the Snippet, which fails.


        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setLibelle(null);

        // Create the Snippet, which fails.


        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setDescription(null);

        // Create the Snippet, which fails.


        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setTextCode(null);

        // Create the Snippet, which fails.


        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setCreationDate(null);

        // Create the Snippet, which fails.


        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSnippets() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);

        // Get all the snippetList
        restSnippetMockMvc.perform(get("/api/snippets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(snippet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].textCode").value(hasItem(DEFAULT_TEXT_CODE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].gitlabExternalCode").value(hasItem(DEFAULT_GITLAB_EXTERNAL_CODE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSnippet() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);

        // Get the snippet
        restSnippetMockMvc.perform(get("/api/snippets/{id}", snippet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(snippet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.textCode").value(DEFAULT_TEXT_CODE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.gitlabExternalCode").value(DEFAULT_GITLAB_EXTERNAL_CODE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSnippet() throws Exception {
        // Get the snippet
        restSnippetMockMvc.perform(get("/api/snippets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSnippet() throws Exception {
        // Initialize the database
        snippetService.save(snippet);

        int databaseSizeBeforeUpdate = snippetRepository.findAll().size();

        // Update the snippet
        Snippet updatedSnippet = snippetRepository.findById(snippet.getId()).get();
        // Disconnect from session so that the updates on updatedSnippet are not directly saved in db
        em.detach(updatedSnippet);
        updatedSnippet
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .textCode(UPDATED_TEXT_CODE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .actif(UPDATED_ACTIF)
            .gitlabExternalCode(UPDATED_GITLAB_EXTERNAL_CODE);

        restSnippetMockMvc.perform(put("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSnippet)))
            .andExpect(status().isOk());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeUpdate);
        Snippet testSnippet = snippetList.get(snippetList.size() - 1);
        assertThat(testSnippet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSnippet.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSnippet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSnippet.getTextCode()).isEqualTo(UPDATED_TEXT_CODE);
        assertThat(testSnippet.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testSnippet.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSnippet.isActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testSnippet.getGitlabExternalCode()).isEqualTo(UPDATED_GITLAB_EXTERNAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSnippet() throws Exception {
        int databaseSizeBeforeUpdate = snippetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSnippetMockMvc.perform(put("/api/snippets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(snippet)))
            .andExpect(status().isBadRequest());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSnippet() throws Exception {
        // Initialize the database
        snippetService.save(snippet);

        int databaseSizeBeforeDelete = snippetRepository.findAll().size();

        // Delete the snippet
        restSnippetMockMvc.perform(delete("/api/snippets/{id}", snippet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
