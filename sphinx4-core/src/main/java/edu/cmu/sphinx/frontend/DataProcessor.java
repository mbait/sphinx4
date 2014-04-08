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
 * @see FrontEnd
 */
package edu.cmu.sphinx.frontend;

import edu.cmu.sphinx.decoder.search.Token;
import edu.cmu.sphinx.frontend.endpoint.SpeechClassifiedData;
import edu.cmu.sphinx.frontend.endpoint.SpeechEndSignal;
import edu.cmu.sphinx.frontend.endpoint.SpeechStartSignal;
import edu.cmu.sphinx.util.props.Configurable;


/**
 * A processor that performs a signal processing function.
 */
public interface DataProcessor extends Configurable {

    Data process(FloatData data) throws DataProcessingException;

    Data process(DoubleData data) throws DataProcessingException;

    Data process(SpeechClassifiedData data) throws DataProcessingException;

    Data process(DataStartSignal signal) throws DataProcessingException;

    Data process(DataEndSignal signal) throws DataProcessingException;

    Data process(SpeechStartSignal signal) throws DataProcessingException;

    Data process(SpeechEndSignal signal) throws DataProcessingException;

    Data process(Token token) throws DataProcessingException;
}