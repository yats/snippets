package com.yats.app.web.rest;

import com.yats.app.domain.Snippet;
import com.yats.app.service.SnippetService;
import com.yats.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.yats.app.domain.Snippet}.
 */
@RestController
@RequestMapping("/api")
public class SnippetResource {

    private final Logger log = LoggerFactory.getLogger(SnippetResource.class);

    private static final String ENTITY_NAME = "snippet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnippetService snippetService;

    public SnippetResource(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    /**
     * {@code POST  /snippets} : Create a new snippet.
     *
     * @param snippet the snippet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new snippet, or with status {@code 400 (Bad Request)} if the snippet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/snippets")
    public ResponseEntity<Snippet> createSnippet(@Valid @RequestBody Snippet snippet) throws URISyntaxException {
        log.debug("REST request to save Snippet : {}", snippet);
        if (snippet.getId() != null) {
            throw new BadRequestAlertException("A new snippet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Snippet result = snippetService.save(snippet);
        return ResponseEntity.created(new URI("/api/snippets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /snippets} : Updates an existing snippet.
     *
     * @param snippet the snippet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated snippet,
     * or with status {@code 400 (Bad Request)} if the snippet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the snippet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/snippets")
    public ResponseEntity<Snippet> updateSnippet(@Valid @RequestBody Snippet snippet) throws URISyntaxException {
        log.debug("REST request to update Snippet : {}", snippet);
        if (snippet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Snippet result = snippetService.save(snippet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, snippet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /snippets} : get all the snippets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of snippets in body.
     */
    @GetMapping("/snippets")
    public ResponseEntity<List<Snippet>> getAllSnippets(Pageable pageable) {
        log.debug("REST request to get a page of Snippets");
        Page<Snippet> page = snippetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /snippets/:id} : get the "id" snippet.
     *
     * @param id the id of the snippet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snippet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/snippets/{id}")
    public ResponseEntity<Snippet> getSnippet(@PathVariable Long id) {
        log.debug("REST request to get Snippet : {}", id);
        Optional<Snippet> snippet = snippetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(snippet);
    }

    /**
     * {@code DELETE  /snippets/:id} : delete the "id" snippet.
     *
     * @param id the id of the snippet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/snippets/{id}")
    public ResponseEntity<Void> deleteSnippet(@PathVariable Long id) {
        log.debug("REST request to delete Snippet : {}", id);

        snippetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
