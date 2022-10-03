import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class LexTest {
    private LexTest() {}

    @Test
    void expect_throws_parse_exception() throws ParseException {
        LexicalAnalyzer lex = new LexicalAnalyzer(InputStream.nullInputStream());

        assertThatThrownBy(() -> lex.expect(Token.VAR, "var keyword", lex.curPos()))
                .isInstanceOf(ParseException.class)
                .hasMessage("var keyword expected at position: ")
                .extracting("ErrorOffset")
                .isEqualTo(1);
    }

    @Test
    void nextChar_throws_exception_when_IO_problems() throws IOException {
        InputStream inputStream = mock(InputStream.class);
        var message = "Oh no, here we go again...";

        when(inputStream.read()).thenThrow(new IOException(message));

        assertThatThrownBy(() -> new LexicalAnalyzer(inputStream))
                .isInstanceOf(ParseException.class)
                .hasMessage(message)
                .extracting("ErrorOffset")
                .isEqualTo(1);

        verify(inputStream).read();
        verifyNoMoreInteractions(inputStream);

    }


}
