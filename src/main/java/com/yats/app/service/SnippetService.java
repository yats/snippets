package com.yats.app.service;

import com.yats.app.domain.Snippet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Snippet}.
 */
public interface SnippetService {

    /**
     * Save a snippet.
     *
     * @param snippet the entity to save.
     * @return the persisted entity.
     */
    Snippet save(Snippet snippet);

    /**
     * Get all the snippets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Snippet> findAll(Pageable pageable);


    /**
     * Get the "id" snippet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Snippet> findOne(Long id);

    /**
     * Delete the "id" snippet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
