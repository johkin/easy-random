/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.randomizers.range;

import static org.jeasy.random.randomizers.range.LocalDateTimeRangeRandomizer.aNewLocalDateTimeRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalDateTime> {

    private LocalDateTime minDateTime, maxDateTime;

    @BeforeEach
    void setUp() {
        minDateTime = LocalDateTime.MIN;
        maxDateTime = LocalDateTime.MAX;
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, maxDateTime);
    }

    @Test
    void generatedLocalDateTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedLocalDateTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minDateTime, maxDateTime);
    }

    @Test
    void generatedLocalDateTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, maxDateTime, SEED);
        LocalDateTime expected = LocalDateTime.of(LocalDate.ofEpochDay(163024688248L), LocalTime.ofSecondOfDay(62481));

        // When
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinDateTimeIsAfterMaxDateTime_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewLocalDateTimeRangeRandomizer(maxDateTime, minDateTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinDateTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(null, maxDateTime);

        // When
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxDateTime);
    }

    @Test
    void whenSpecifiedMaxDateTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, null);

        // when
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minDateTime);
    }

}
