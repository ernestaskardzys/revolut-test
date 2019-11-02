package info.ernestas.revoluttest.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AccountNumberUtilTest {

    private static final int ACCOUNT_NUMBER_LENGTH = 64;

    @ParameterizedTest
    @ValueSource(strings = {
            "John Doe",
            "Jane Doe",
            "Spider Man"
    })
    void generateAccountNumber(String name) {
        String result = AccountNumberUtil.generateAccountNumber(name);

        assertThat(result.length(), is(ACCOUNT_NUMBER_LENGTH));
    }
}