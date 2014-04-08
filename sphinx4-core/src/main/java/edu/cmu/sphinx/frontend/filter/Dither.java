/*
 * Copyright 1999-2004 Carnegie Mellon University.  
 * Portions Copyright 2002-2004 Sun Microsystems, Inc.  
 * Portions Copyright 2002-2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 *
 */

package edu.cmu.sphinx.frontend.filter;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Random;

import edu.cmu.sphinx.frontend.*;
import edu.cmu.sphinx.util.props.*;

/**
 * Implements a dither for the incoming packet. A small amount of random noise is added
 * to the signal to avoid floating point errors and prevent the energy from
 * being zero. <p/> Other {@link Data} objects are passed along unchanged through
 * this Dither processor. <p/> See also {@link EnergyFilter}, an alternative to Dither.
 */
public class Dither extends BaseDataProcessor {

    /** The maximal value which could be added/subtracted to/from the signal*/
    @S4Double(defaultValue = 2.0)
    public static final String PROP_MAX_DITHER = "maxDither";
    private double ditherMax;

    /** The maximal value of dithered values. */
    @S4Double(defaultValue = Double.MAX_VALUE)
    public static final String PROP_MAX_VAL = "upperValueBound";
    private double maxValue;

    /** The minimal value of dithered values. */
    @S4Double(defaultValue = -Double.MAX_VALUE)
    public static final String PROP_MIN_VAL = "lowerValueBound";
    private double minValue;


    /** The property about using random seed or not */
    @S4Boolean(defaultValue = false)
    public static final String PROP_USE_RANDSEED = "useRandSeed";
    private boolean useRandSeed;
    Random r;

    public Dither(  double ditherMax, boolean useRandSeed, double maxValue, double minValue) {
        initLogger();

        this.ditherMax = ditherMax;
        this.useRandSeed = useRandSeed;

        this.maxValue = maxValue;
        this.minValue = minValue;
        initialize();
    }

    public Dither( ) {

    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);

        ditherMax = ps.getDouble(PROP_MAX_DITHER);
        useRandSeed = ps.getBoolean(PROP_USE_RANDSEED);

        maxValue = ps.getDouble(PROP_MAX_VAL);
        minValue = ps.getDouble(PROP_MIN_VAL);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (useRandSeed)
            r = new Random();
        else
            r = new Random(12345);
    }


    /**
     * Process data, adding dither
     *
     * @param data a frame
     * @return processed frame
     * @throws IllegalArgumentException
     */
    @Override
    public Data process(DoubleData data) throws DataProcessingException {
        if (data == null || ditherMax == 0) {
            return data;
        }

        double[] inFeatures = data.getValues();
        double[] outFeatures = new double[inFeatures.length];
        for (int i = 0; i < inFeatures.length; ++i) {
            outFeatures[i] =
                    r.nextFloat() * 2 * ditherMax - ditherMax + inFeatures[i];
            outFeatures[i] = max(min(outFeatures[i], maxValue), minValue);
        }

        return new DoubleData(outFeatures, data.getSampleRate(),
                              data.getFirstSampleNumber());
    }
}
