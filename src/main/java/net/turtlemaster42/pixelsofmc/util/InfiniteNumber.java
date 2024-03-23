package net.turtlemaster42.pixelsofmc.util;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import java.util.ArrayList;
import java.util.List;

public class InfiniteNumber {

    // not truly infinite, it can't get bigger than a number consisting of a 1 with 38654705646 zeros - 1
    // in other words, the maximum number is 10^38654705646 - 1, for context, the observable universe
    // measured in plancklength (smallest measurable length) is about 5,44*10^61, soooooo, I don't think you wil ever hit the limit
    // still not as big as a googleplex though.

    private List<Long> INFINITE_NUMBER = new ArrayList<>();
    private final long MAX_LIST_VALUE = 1000000000000000000L;

    public List<Long> getInfiniteNumber() {
        return INFINITE_NUMBER;
    }

    public boolean isNegative() {
        return INFINITE_NUMBER.get(INFINITE_NUMBER.size() - 1) < 0;
    }

    public void cleanInfiniteNumber() {
        for (int i = INFINITE_NUMBER.size() - 1; i > 0 ; i--) {
            if (INFINITE_NUMBER.get(i).equals(0L)) {
                INFINITE_NUMBER.remove(i);
            } else {
                break;
            }
        }
    }



    public String toString() {
        if (INFINITE_NUMBER.isEmpty()) {
            return "0";
        }
        StringBuilder numberString = new StringBuilder();
        for (int i = INFINITE_NUMBER.size() - 1; i > -1; i--) {
            long number = INFINITE_NUMBER.get(i);
            if (i != INFINITE_NUMBER.size() - 1) {
                int addedZeros = 0;
                while (String.valueOf(number).length() + addedZeros < String.valueOf(MAX_LIST_VALUE).length() - 1) {
                    numberString.append(0);
                    addedZeros++;
                }
            }
            numberString.append(number);
        }
        return numberString.toString();
    }

    public String funkyToString() {
        if (INFINITE_NUMBER.isEmpty()) {
            return "0";
        }
        StringBuilder numberString = new StringBuilder();
        for (int i = INFINITE_NUMBER.size() - 1; i > -1; i--) {
            long number = INFINITE_NUMBER.get(i);
            if (i != INFINITE_NUMBER.size() - 1) {
                int addedZeros = 0;
                while (String.valueOf(number).length() + addedZeros < String.valueOf(MAX_LIST_VALUE).length() - 1) {
                    numberString.append(0);
                    addedZeros++;
                }
            }
            numberString.append(number);
            numberString.append("_");
        }
        return numberString.toString();
    }


    public InfiniteNumber fromString(String string) {
        INFINITE_NUMBER.clear();
        StringBuilder newString = new StringBuilder();
        for (int i = string.length() - 1; i > -1; i--) {
            char currentChar = string.charAt(i);
            newString.append(currentChar);
            if (String.valueOf(currentChar).equals("-"))
                break;

            if (newString.toString().length() >= String.valueOf(MAX_LIST_VALUE).length() - 1) {
                INFINITE_NUMBER.add(Long.valueOf(newString.reverse().toString()));
                newString = new StringBuilder();
            }
        }
        if (newString.toString().equals("-")) {
            INFINITE_NUMBER.set(INFINITE_NUMBER.size() - 1, -INFINITE_NUMBER.get(INFINITE_NUMBER.size() - 1));
        } else {
            if (newString.toString().isEmpty()) {
                PixelsOfMc.LOGGER.error("tried to get Long.valueOf() from empty string, setting string to 0, (in: {}, infNum: {})", string, INFINITE_NUMBER);
                newString.append(0);
            }
            INFINITE_NUMBER.add(Long.valueOf(newString.reverse().toString()));
        }
        return this;
    }

    public InfiniteNumber fromInt(int num) {
        INFINITE_NUMBER.clear();
        INFINITE_NUMBER.add((long) num);
        return this;
    }

    public InfiniteNumber fromLong(long num) {
        INFINITE_NUMBER.clear();
        long num2 = 0;

        if (num > 0) {
            while (num > MAX_LIST_VALUE) {
                num2 += 1;
                num -= MAX_LIST_VALUE;
            }
        } else {
            while (num < -MAX_LIST_VALUE) {
                num2 -= 1;
                num += MAX_LIST_VALUE;
            }
        }

        INFINITE_NUMBER.add(num);
        if (num2 != 0) {
            INFINITE_NUMBER.add(num2);
        }
        return this;
    }

    public InfiniteNumber fromInfiniteNumber(InfiniteNumber infiniteNumber) {
        INFINITE_NUMBER.clear();
        this.add(infiniteNumber);
        return this;
    }

    public InfiniteNumber copy() {
        return new InfiniteNumber().fromInfiniteNumber(this);
    }

    public boolean equals(InfiniteNumber infiniteNumber) {
        return super.equals(infiniteNumber);
    }

    public boolean equals(int number) {
        if (INFINITE_NUMBER.size() == 1) {
            return INFINITE_NUMBER.get(0).equals((long) number);
        }
        return false;
    }

    public boolean equals(long number) {
        if (INFINITE_NUMBER.size() == 2) {
            if (INFINITE_NUMBER.get(1) >= -9) {
                return INFINITE_NUMBER.get(0).equals(number + 9000000000000000000L);
            }
            if (INFINITE_NUMBER.get(1) <= 9) {
                return INFINITE_NUMBER.get(0).equals(number - 9000000000000000000L);
            }
        }
        if (INFINITE_NUMBER.size() == 1) {
            return INFINITE_NUMBER.get(0).equals(number);
        }
        return false;
    }

    public boolean equals(String string) {
        InfiniteNumber infNumber = new InfiniteNumber();
        infNumber.fromString(string);
        return equals(infNumber);
    }

    public boolean isBiggerThen(InfiniteNumber infNumber) {
        if (INFINITE_NUMBER.size() > infNumber.getInfiniteNumber().size()) {
            return true;
        } else if (INFINITE_NUMBER.size() < infNumber.getInfiniteNumber().size()) {
            return false;
        } else {
            int maxIndex = INFINITE_NUMBER.size() - 1;
            return INFINITE_NUMBER.get(maxIndex) > infNumber.getInfiniteNumber().get(maxIndex);
        }
    }

    public boolean isSmallerThen(InfiniteNumber infNumber) {
        if (INFINITE_NUMBER.size() < infNumber.getInfiniteNumber().size()) {
            return true;
        } else if (INFINITE_NUMBER.size() > infNumber.getInfiniteNumber().size()) {
            return false;
        } else {
            int maxIndex = INFINITE_NUMBER.size() - 1;
            return INFINITE_NUMBER.get(maxIndex) < infNumber.getInfiniteNumber().get(maxIndex);
        }
    }

    public boolean isBiggerThenOrEquals(InfiniteNumber infNumber) {
        return isBiggerThen(infNumber) || equals(infNumber);
    }

    public boolean isSmallerThenOrEquals(InfiniteNumber infNumber) {
        return isSmallerThen(infNumber) || equals(infNumber);
    }

    public boolean isBiggerThen(int num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromInt(num);
        return isBiggerThen(infiniteNumber);
    }

    public boolean isSmallerThen(int num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromInt(num);
        return isSmallerThen(infiniteNumber);
    }

    public boolean isBiggerThenOrEquals(int num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromInt(num);
        return isBiggerThenOrEquals(infiniteNumber);
    }

    public boolean isSmallerThenOrEquals(int num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromInt(num);
        return isSmallerThenOrEquals(infiniteNumber);
    }

    public boolean isBiggerThen(long num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromLong(num);
        return isBiggerThen(infiniteNumber);
    }

    public boolean isSmallerThen(long num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromLong(num);
        return isSmallerThen(infiniteNumber);
    }

    public boolean isBiggerThenOrEquals(long num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromLong(num);
        return isBiggerThenOrEquals(infiniteNumber);
    }

    public boolean isSmallerThenOrEquals(long num) {
        InfiniteNumber infiniteNumber = new InfiniteNumber().fromLong(num);
        return isSmallerThenOrEquals(infiniteNumber);
    }

    public int toInt() {
        if (INFINITE_NUMBER.isEmpty()) {
            return 0;
        }
        cleanInfiniteNumber();

        if (this.isNegative()) {
            if (INFINITE_NUMBER.size() == 1) {
                if (INFINITE_NUMBER.get(0) > Integer.MIN_VALUE) {
                    return INFINITE_NUMBER.get(0).intValue();
                }
            }
            return Integer.MIN_VALUE;
        } else {
            if (INFINITE_NUMBER.size() == 1) {
                if (INFINITE_NUMBER.get(0) < Integer.MAX_VALUE) {
                    return INFINITE_NUMBER.get(0).intValue();
                }
            }
            return Integer.MAX_VALUE;
        }
    }

    public long toLong() {
        if (INFINITE_NUMBER.isEmpty()) {
            return 0L;
        }
        cleanInfiniteNumber();

        if (INFINITE_NUMBER.size() == 1) {
            return INFINITE_NUMBER.get(0);
        }
        if (INFINITE_NUMBER.size() == 2) {
            if (INFINITE_NUMBER.get(1) >= -9) {
                if (INFINITE_NUMBER.get(0) <= Long.MIN_VALUE + 9000000000000000000L) {
                    return 1000000000000000000L * INFINITE_NUMBER.get(1) - INFINITE_NUMBER.get(0);
                }
            }
            if (INFINITE_NUMBER.get(1) <= 9) {
                if (INFINITE_NUMBER.get(0) >= Long.MAX_VALUE - 9000000000000000000L) {
                    return 1000000000000000000L * INFINITE_NUMBER.get(1) + INFINITE_NUMBER.get(0);
                }
            }
        }
        if (isNegative()) {
            return Long.MIN_VALUE;
        }
        else {
            return Long.MAX_VALUE;
        }
    }


    public void add(int number) {
        add(new InfiniteNumber().fromInt(number));
    }

    public void add(long number) {
        add(new InfiniteNumber().fromLong(number));
    }

    public void add(InfiniteNumber infiniteNumber) {
        if (INFINITE_NUMBER.isEmpty()) {
            INFINITE_NUMBER.add(0L);
        }

        if (this.isNegative() && infiniteNumber.isNegative()) {
            infiniteNumber.abs();
            this.abs();
            simpleAdd(infiniteNumber);
            this.invert();

        } else if (!this.isNegative() && !infiniteNumber.isNegative()) {
            simpleAdd(infiniteNumber);

        } else if (!this.isNegative() && infiniteNumber.isNegative()) {
            infiniteNumber.abs();

            if (this.isBiggerThen(infiniteNumber)) {
                simpleSubtract(infiniteNumber);
            } else {
                infiniteNumber.simpleSubtract(this);
                INFINITE_NUMBER = infiniteNumber.getInfiniteNumber();
                this.invert();
            }

        } else {
            this.abs();

            if (this.isBiggerThen(infiniteNumber)) {
                simpleSubtract(infiniteNumber);
                this.invert();
            } else {
                infiniteNumber.simpleSubtract(this);
                INFINITE_NUMBER = infiniteNumber.getInfiniteNumber();
            }
        }
    }

    public void subtract(int number) {
        add(-number);
    }

    public void subtract(long number) {
        add(-number);
    }

    public void subtract(InfiniteNumber infiniteNumber) {
        infiniteNumber.invert();
        add(infiniteNumber);
    }

    public void abs() {
        INFINITE_NUMBER.set(INFINITE_NUMBER.size() - 1,  Math.abs(INFINITE_NUMBER.get(INFINITE_NUMBER.size() - 1)));
    }

    public void invert() {
        INFINITE_NUMBER.set(INFINITE_NUMBER.size() - 1, INFINITE_NUMBER.get(INFINITE_NUMBER.size() - 1) * -1);
    }

    public long getCrudePercentage(InfiniteNumber max, InfiniteNumber partial) {
        if (max.getInfiniteNumber().isEmpty()) {
            return 100;
        }
        if (partial.getInfiniteNumber().isEmpty() || partial.equals(0)) {
            return 0;
        }

        String maxString = max.toString();
        String partialString = partial.toString();

        int maxDigits = Integer.parseInt(maxString.substring(0, 4));
        int partialDigits = Integer.parseInt(partialString.substring(0, 4));
        int maxLength = maxString.length();
        int partialLength = partialString.length();

        return Math.round((1f / maxDigits * partialDigits) * Math.pow(10, 2 - maxLength + partialLength));
    }



    protected void simpleAdd(InfiniteNumber infiniteNumber) {
        // checks if both inputs are positive
        if (infiniteNumber.isNegative() || this.isNegative()) {
            PixelsOfMc.LOGGER.error("can only add two positive numbers! {} or {} is negative", this, infiniteNumber);
            return;
        }
        List<Long> number = infiniteNumber.getInfiniteNumber();
        // loops over the inputted number list
        for (int i = 0; i < number.size(); i++) {
            // checks if the list number is 0, if so, it does not need to be added
            if (number.get(i) == 0) {
                continue;
            }

            // checks if the inputted number is bigger then the current number, if so, just push the number to current number list
            if (i > INFINITE_NUMBER.size() - 1) {
                INFINITE_NUMBER.add(number.get(i));
                continue;
            }

            // simply adds
            basic_add_2(number.get(i), i);
        }
    }

    protected void simpleSubtract(InfiniteNumber infiniteNumber) {
        // checks if both inputs are positive
        if (infiniteNumber.isNegative() || this.isNegative()) {
            PixelsOfMc.LOGGER.error("can only subtract two positive numbers! {} or {} is negative", this, infiniteNumber);
            return;
        }
        List<Long> number = infiniteNumber.getInfiniteNumber();
        // loops over the inputted number list
        for (int i = 0; i < number.size(); i++) {
            // checks if the list number is 0, if so, it does not need to be added
            if (number.get(i) == 0) {
                continue;
            }

            // checks if the inputted number is bigger then the current number, if so, just push the number to current number list
            if (i > INFINITE_NUMBER.size() - 1) {
                INFINITE_NUMBER.add(number.get(i));
                continue;
            }

            // simply subtracts
            basic_subtract(number.get(i), i);
        }
    }

    protected void basic_add(long number, int list_start) {
        if (INFINITE_NUMBER.isEmpty()) {
            INFINITE_NUMBER.add(0L);
        }

        long value = number;
        for (int i = list_start; i < INFINITE_NUMBER.size(); i++) {

            boolean negative = isNegative();
            long infiniteNumber = INFINITE_NUMBER.get(i);
            if (negative && INFINITE_NUMBER.size() > i + 1) {
                value = -value;
            }
            long newNumber = infiniteNumber + value;
            value = 0;

            if (newNumber >= MAX_LIST_VALUE) {
                newNumber = newNumber - MAX_LIST_VALUE;
                if (i + 1 < INFINITE_NUMBER.size()) {
                    if (negative) {
                        value = -1;
                    } else {
                        value = 1;
                    }
                } else {
                    INFINITE_NUMBER.add(1L);
                }
            }
            if (newNumber <= -MAX_LIST_VALUE) {
                newNumber = -newNumber - MAX_LIST_VALUE;
                if (i + 1 < INFINITE_NUMBER.size()) {
                    if (negative) {
                        value = 1;
                    } else {
                        value = -1;
                    }
                } else {
                    INFINITE_NUMBER.add(-1L);
                }
            }

            if (newNumber < 0) {
                if (i + 1 < INFINITE_NUMBER.size()) {
                    newNumber = MAX_LIST_VALUE + newNumber;
                    if (negative) {
                        value = 1;
                        if (i + 1 == INFINITE_NUMBER.size() - 1 && INFINITE_NUMBER.get(INFINITE_NUMBER.size() - 1) + value == 0) {
                            newNumber = -newNumber;
                        }
                    } else {
                        value = -1;
                    }
                }
            }
            INFINITE_NUMBER.set(i, newNumber);
        }

        int loop = INFINITE_NUMBER.size() - 1;
         while (INFINITE_NUMBER.get(loop) == 0) {
            INFINITE_NUMBER.remove(loop);
            loop = loop - 1;
            if (loop < 0) {
                break;
            }
        }
    }

    protected void basic_add_2(long number, int list_start) {
        if (INFINITE_NUMBER.isEmpty()) {
            INFINITE_NUMBER.add(0L);
        }

        // makes sure no mistakes can happen
        if (this.isNegative() || number < 0) {
            PixelsOfMc.LOGGER.error("can only add two positive numbers! {} or {} is negative", this, number);
            return;
        }

        long value = number;
        for (int i = list_start; i < INFINITE_NUMBER.size(); i++) {
            long infiniteNumber = INFINITE_NUMBER.get(i);

            long newNumber = infiniteNumber + value;
            value = 0;

            while (newNumber >= MAX_LIST_VALUE) {
                newNumber = newNumber - MAX_LIST_VALUE;
                value += 1;
            }
            INFINITE_NUMBER.set(i, newNumber);
        }
    }

    protected void basic_subtract(long number, int list_start) {
        // makes sure INFINITE_NUMBER exists
        if (INFINITE_NUMBER.isEmpty()) {
            INFINITE_NUMBER.add(0L);
        }

        // makes sure no mistakes can happen
        if (this.isSmallerThen(number)) {
            PixelsOfMc.LOGGER.error("the number to subtract should not be bigger then INFINITE_NUMBER: {} > {}", number, this);
            return;
        }
        if (this.isNegative() || number < 0) {
            PixelsOfMc.LOGGER.error("can only subtract two positive numbers! {} or {} is negative", this, number);
            return;
        }

        long value = number;
        for (int i = list_start; i < INFINITE_NUMBER.size(); i++) {
            long infiniteNumber = INFINITE_NUMBER.get(i);

            long newNumber = infiniteNumber - value;
            value = 0;

            while (newNumber < 0) {
                newNumber = MAX_LIST_VALUE + newNumber;
                value += 1;

            }
            INFINITE_NUMBER.set(i, newNumber);
        }
    }
}
