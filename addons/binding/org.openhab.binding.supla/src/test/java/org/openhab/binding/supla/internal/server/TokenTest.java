package org.openhab.binding.supla.internal.server;

import java.time.LocalDateTime;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;;

public class TokenTest {

    @Test
    public void shouldBeValidBecauseGivenDateIsBeforeExirationTime() {

        // given
        final int validTimeInSeconds = 30;
        LocalDateTime createDate = LocalDateTime.of(2017, 6, 25, 19, 36, 15);
        Token token = new Token("token", validTimeInSeconds, createDate);

        LocalDateTime checkDate = LocalDateTime.of(2017, 6, 25, 19, 36, 15 + validTimeInSeconds - 1);

        // when
        final boolean valid = token.isValid(checkDate);

        // then
        assertThat(valid, equalTo(true));
    }

    @Test
    public void shouldBeNotValidBecauseGivenDateIsAfterExpirationTime() {

        // given
        final int validTimeInSeconds = 30;
        LocalDateTime createDate = LocalDateTime.of(2017, 6, 25, 19, 36, 15);
        Token token = new Token("token", validTimeInSeconds, createDate);

        LocalDateTime checkDate = LocalDateTime.of(2017, 6, 25, 19, 36, 15 + validTimeInSeconds);

        // when
        final boolean valid = token.isValid(checkDate);

        // then
        assertThat(valid, equalTo(false));
    }
}
