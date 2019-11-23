/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.requireNonNull;

/**
 * Handles creation and manipulation of time intervals.
 *
 * @author mab90
 */
public class Interval {

    /**
     * The default date of the interval.
     */
    private static final LocalDate DEFAULT_DATE = LocalDate.now();

    /**
     * The default start time of the interval.
     */
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(12, 0);

    /**
     * The default end time of the interval.
     */
    private static final LocalTime DEFAULT_END_TIME = LocalTime.of(13, 0);

    /**
     * The default zone id for the interval.
     */
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * The start of for the interval.
     */
    private LocalDate startDate;

    /**
     * The end date of the interval.
     */
    private LocalDate endDate;

    /**
     * The start time of the interval.
     */
    private LocalTime startTime;

    /**
     * The end time of the interval.
     */
    private LocalTime endTime;

    /**
     * The start date and time of the interval represented as a
     * {@link LocalDateTime}.
     */
    private LocalDateTime startDateTime;

    /**
     * The end date and time of the interval represented as a
     * {@link LocalDateTime}.
     */
    private LocalDateTime endDateTime;

    /**
     * The zone id for the interval.
     */
    private ZoneId zoneId;

    /**
     * The start date and time of the interval represented as a
     * {@link ZonedDateTime}.
     */
    private ZonedDateTime zonedStartDateTime;

    /**
     * The end date and time of the interval represented as a
     * {@link ZonedDateTime}.
     */
    private ZonedDateTime zonedEndDateTime;

    /**
     * The start milliseconds of the interval.
     */
    private long startMilliseconds = Long.MIN_VALUE;

    /**
     * The end milliseconds of the interval.
     */
    private long endMilliseconds = Long.MAX_VALUE;

    public Interval() {
        this(DEFAULT_DATE, DEFAULT_START_TIME, DEFAULT_DATE, DEFAULT_END_TIME, DEFAULT_ZONE_ID);
    }

    public Interval(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this(startDate, startTime, endDate, endTime, DEFAULT_ZONE_ID);
    }

    public Interval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this(startDateTime, endDateTime, DEFAULT_ZONE_ID);
    }

    public Interval(LocalDateTime startDateTime, LocalDateTime endDateTime, ZoneId zoneId) {
        this(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime(), zoneId);
    }

    public Interval(ZonedDateTime zonedStartDateTime, ZonedDateTime zonedEndDateTime) {
        this(zonedStartDateTime.toLocalDateTime(), zonedEndDateTime.toLocalDateTime(), zonedStartDateTime.getZone());
    }

    public Interval(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, ZoneId zoneId) {
        this.startDate = requireNonNull(startDate);
        this.startTime = requireNonNull(startTime);
        this.endDate = requireNonNull(endDate);
        this.endTime = requireNonNull(endTime);
        this.zoneId = requireNonNull(zoneId);

        // Check to make sure that the start date is before the end date
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "The start date of an Interval cannot occur after the end date of the Interval"
            );
        }

        // Check to make sure that the start time is before the end time if the dates are equal
        if (startDate.equals(endDate)) {
            if (startTime.isAfter(endTime)) {
                throw new IllegalArgumentException(
                        "The start time of an Interval cannot occur after the end time of the Interval"
                );
            }
        }
    }

    /**
     * Gets the start date of the interval.
     *
     * @return The start date of the interval.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the start time of the interval.
     *
     * @return The start time of the interval.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the zoned start datetime of the interval.
     *
     * @return The zoned start datetime of the interval.
     */
    public ZonedDateTime getZonedStartDateTime() {
        return zonedStartDateTime == null ? ZonedDateTime.of(startDate, startTime, zoneId) : zonedStartDateTime;
    }

    /**
     * Gets the end date of the interval.
     *
     * @return The end date of the interval.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets the end time of the interval.
     *
     * @return The end time of the interval.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the zoned end datetime of the interval.
     *
     * @return The zoned end datetime of the interval.
     */
    public ZonedDateTime getZonedEndDateTime() {
        return zonedEndDateTime == null ? ZonedDateTime.of(endDate, endTime, zoneId) : zonedEndDateTime;
    }

    /**
     * Gets the duration of the interval.
     *
     * @return The duration of the interval.
     */
    public Duration getDuration() {
        return Duration.between(getZonedStartDateTime(), getZonedEndDateTime());
    }

    /**
     * Checks whether two intervals overlap one another. See discussion in
     * <a href="https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap">this</a>
     * post.
     *
     * @param other The other interval to check.
     * @return True if the intervals overlap one another, otherwise false.
     */
    public boolean overlapsWith(Interval other) {
        LocalDate maxStart = startDate.isAfter(other.startDate) ? startDate : other.startDate;
        LocalDate minEnd = endDate.isBefore(other.endDate) ? endDate : other.endDate;
        return maxStart.isBefore(minEnd) || maxStart.equals(minEnd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Interval other = (Interval) o;
        return startDate.equals(other.startDate)
                && startTime.equals(other.startTime)
                && endDate.equals(other.endDate)
                && endTime.equals(other.endTime)
                && zoneId.equals(other.zoneId);
    }

    @Override
    public int hashCode() {
        final int prime = 7919;
        int result = 1;
        result = result * prime + startDate.hashCode();
        result = result * prime + startTime.hashCode();
        result = result * prime + endDate.hashCode();
        result = result * prime + startTime.hashCode();
        result = result * prime + zoneId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Interval[startDate = ");
        sb.append(startDate);

        sb.append(", startTime = ");
        sb.append(startTime);

        sb.append(", endDate = ");
        sb.append(endDate);

        sb.append(", endTime = ");
        sb.append(endTime);

        sb.append(", zoneId = ");
        sb.append(zoneId);

        return sb.toString();
    }

}
