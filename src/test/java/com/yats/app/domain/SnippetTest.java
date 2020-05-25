package com.yats.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.yats.app.web.rest.TestUtil;

public class SnippetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Snippet.class);
        Snippet snippet1 = new Snippet();
        snippet1.setId(1L);
        Snippet snippet2 = new Snippet();
        snippet2.setId(snippet1.getId());
        assertThat(snippet1).isEqualTo(snippet2);
        snippet2.setId(2L);
        assertThat(snippet1).isNotEqualTo(snippet2);
        snippet1.setId(null);
        assertThat(snippet1).isNotEqualTo(snippet2);
    }
}
