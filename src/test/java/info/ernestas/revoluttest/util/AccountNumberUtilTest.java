package info.ernestas.revoluttest.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

class AccountNumberUtilTest {

    private static final String ALPHANUMERICAL_64_SYMBOLS_LENGTH = "[A-Za-z0-9]{64}";

    @ParameterizedTest
    @ValueSource(strings = {
            "John Doe",
            "Jane Doe",
            "Spider Man"
    })
    void generateAccountNumber(String name) {
        String result = AccountNumberUtil.generateAccountNumber(name);

        assertThat(result, matchesPattern(ALPHANUMERICAL_64_SYMBOLS_LENGTH));
    }
}