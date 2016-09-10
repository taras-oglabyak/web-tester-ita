package com.softserve.webtester.model;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Enumeration of the variable data types can be used in the {@link Variable}
 * instance.
 * 
 * @author Taras Oglabyak
 */
public enum VariableDataType {

    DIGIT {
        @Override
        public Stream<?> getRandomStream(int length) {
            return new Random().ints(AsciiConstants.DIGIT_0, AsciiConstants.DIGIT_9).mapToObj(i -> (char) i)
                    .limit(length);
        }
    },

    DIGIT_FLOAT {
        @Override
        public Stream<?> getRandomStream(int length) {
            return new Random().ints(AsciiConstants.DIGIT_0, AsciiConstants.DIGIT_9).mapToObj(i -> (char) i)
                    .limit(length + 2);
        }
    },

    STRING {
        @Override
        public Stream<?> getRandomStream(int length) {
            return new Random()
                    .ints(AsciiConstants.DIGIT_0, AsciiConstants.CHAR_LOW_Z)
                    .filter(i -> (i < AsciiConstants.DIGIT_9 || i > AsciiConstants.CHAR_A)
                            && (i < AsciiConstants.CHAR_Z || i > AsciiConstants.CHAR_LOW_A)).mapToObj(i -> (char) i)
                    .limit(length);
        }
    };

    public abstract Stream<?> getRandomStream(int length);

    private static class AsciiConstants {
        public static final int DIGIT_0 = 48;
        public static final int DIGIT_9 = 58;
        public static final int CHAR_A = 65;
        public static final int CHAR_Z = 90;
        public static final int CHAR_LOW_A = 97;
        public static final int CHAR_LOW_Z = 122;
    }

}