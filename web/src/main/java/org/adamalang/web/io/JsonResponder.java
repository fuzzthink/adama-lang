package org.adamalang.web.io;

import org.adamalang.runtime.exceptions.ErrorCodeException;

public interface JsonResponder {
    /** stream an update */
    public void stream(String json);

    /** respond in a terminal fashion */
    public void finish(String json);

    /** respond with a terminal error */
    public void error(ErrorCodeException ex);
}
