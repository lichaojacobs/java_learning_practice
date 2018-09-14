package com.jacobs.basic.effctive3rd;

public class EnumInterface {
    public interface Operation {
        double apply(double x, double y);
    }

    public enum BasicOperation implements Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },

        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        TIMES("*") {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {
                return x / y;
            }
        };

        private final String symbol;

        BasicOperation(String symbol) {
            this.symbol = symbol;
        }
    }

    public enum ExtendedOperation implements Operation {
        EXP("^") {
            @Override
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },

        REMAINDER("%") {
            @Override
            public double apply(double x, double y) {
                return x % y;
            }
        };
        private final String symbol;

        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }
    }

    public static void main(String[] args) {
        test(ExtendedOperation.class, 2.0, 3.0);
        test(BasicOperation.class, 2.0, 3.0);
    }

    private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}
