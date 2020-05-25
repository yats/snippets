package com.yats.app.repository;

import com.yats.app.domain.Snippet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Snippet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
}
