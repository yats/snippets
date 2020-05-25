package com.yats.app.service.impl;

import com.yats.app.service.SnippetService;
import com.yats.app.domain.Snippet;
import com.yats.app.repository.SnippetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Snippet}.
 */
@Service
@Transactional
public class SnippetServiceImpl implements SnippetService {

    private final Logger log = LoggerFactory.getLogger(SnippetServiceImpl.class);

    private final SnippetRepository snippetRepository;

    public SnippetServiceImpl(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    /**
     * Save a snippet.
     *
     * @param snippet the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Snippet save(Snippet snippet) {
        log.debug("Request to save Snippet : {}", snippet);
        return snippetRepository.save(snippet);
    }

    /**
     * Get all the snippets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Snippet> findAll(Pageable pageable) {
        log.debug("Request to get all Snippets");
        return snippetRepository.findAll(pageable);
    }


    /**
     * Get one snippet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Snippet> findOne(Long id) {
        log.debug("Request to get Snippet : {}", id);
        return snippetRepository.findById(id);
    }

    /**
     * Delete the snippet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Snippet : {}", id);

        snippetRepository.deleteById(id);
    }
}
