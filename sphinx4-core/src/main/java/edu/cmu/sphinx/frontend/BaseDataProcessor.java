/*
 * Copyright 2004 Carnegie Mellon University.  
 * Portions Copyright 2004 Sun Microsystems, Inc.  
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 *
 */

package edu.cmu.sphinx.frontend;

import edu.cmu.sphinx.decoder.search.Token;
import edu.cmu.sphinx.frontend.endpoint.SpeechClassifiedData;
import edu.cmu.sphinx.frontend.endpoint.SpeechEndSignal;
import edu.cmu.sphinx.frontend.endpoint.SpeechStartSignal;
import edu.cmu.sphinx.util.props.ConfigurableAdapter;
import edu.cmu.sphinx.util.Timer;
import edu.cmu.sphinx.util.TimerPool;
import edu.cmu.sphinx.util.Utilities;


/**
 * An abstract DataProcessor implementing elements common to all concrete
 * DataProcessors, such as name, predecessor, and timer.
 */
public abstract class BaseDataProcessor extends ConfigurableAdapter
        implements DataProcessor {

    private Timer timer;

    public BaseDataProcessor() {
    }

    /**
     * Initializes this DataProcessor. This is typically called after the
     * DataProcessor has been configured.
     */
    public void initialize() {
    }

    /**
     * Returns the timer this DataProcessor uses.
     *
     * @return the timer
     */
    public synchronized Timer getTimer() {
        if (timer == null)
            this.timer = TimerPool.getTimer(this,
                                            Utilities.getReadable(getName()));

        return timer;
    }

    public Data process(FloatData data) throws DataProcessingException {
        return data;
    }

    public Data process(DoubleData data) throws DataProcessingException {
        return data;
    }

    public Data process(SpeechClassifiedData data)
            throws DataProcessingException {
        return data;
    }

    public Data process(DataStartSignal signal) throws DataProcessingException {
        return signal;
    }

    public Data process(DataEndSignal signal) throws DataProcessingException {
        return signal;
    }

    public Data process(SpeechEndSignal signal) throws DataProcessingException {
        return signal;
    }

    public Data process(SpeechStartSignal signal)
            throws DataProcessingException {
        return signal;
    }

    public Data process(Token token) throws DataProcessingException {
        return token;
    }
}
