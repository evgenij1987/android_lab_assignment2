package rwth.lab.android.mensaviewer.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by evgenijavstein on 30/04/15.
 *
 * Helper class to get rid of "&"-signs which
 * make the XMLPullParser crash, since "&"-signs should be
 * encoded as &amp; in XML
 * Probably, a better approach would be not to use an XML parser, or to fixing the HTML source.
 * -> Thanks to mhaller at stackoverlow.com
 */
public class XmlSignFilterStream extends InputStream {
    private static final byte[] REPLACEMENT = "amp;".getBytes();
    private final byte[] readBuf = new byte[REPLACEMENT.length];
    private final Deque<Byte> backBuf = new ArrayDeque<Byte>();
    private final InputStream in;

    public XmlSignFilterStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (!backBuf.isEmpty()) {
            return backBuf.pop();
        }
        int first = in.read();
        if (first == '&') {
            peekAndReplace();
        }
        return first;
    }

    private void peekAndReplace() throws IOException {
        int read = super.read(readBuf, 0, REPLACEMENT.length);
        for (int i1 = read - 1; i1 >= 0; i1--) {
            backBuf.push(readBuf[i1]); //first take letters which you would loose else, by just replacing & with amp;
        }
        for (int i = 0; i < REPLACEMENT.length; i++) {
            if (read != REPLACEMENT.length || readBuf[i] != REPLACEMENT[i]) {
                for (int j = REPLACEMENT.length - 1; j >= 0; j--) {
                    // In reverse order
                    backBuf.push(REPLACEMENT[j]); //then appends replacement "amp;" letters
                }
                return;
            }
        }
    }
}



