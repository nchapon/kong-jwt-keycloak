package fr.cnp.examples.jwt.backend;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nchapon on 22/12/17.
 */

public class MessageTest {


    @Test
    public void shouldCreateAMessage() {
        Message m = new Message("Hello admin");
        assertThat(m.getMessage()).isEqualTo("Hello admin");
    }
}
