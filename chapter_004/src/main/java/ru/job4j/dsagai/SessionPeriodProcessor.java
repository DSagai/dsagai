package ru.job4j.dsagai;

import java.util.Date;
import java.util.PriorityQueue;

/**
 * Class determines in which period of time
 * maximum number of client connections appears.
 *
 * @author dsagai
 * @version 000
 * @since 27.03.2017
 */
public class SessionPeriodProcessor {
    //
    private final PriorityQueue<TimePoint> queue = new PriorityQueue<>();

    /**
     * method adds new interval to be processed
     * @param start Date.
     * @param end Date.
     */
    public void pushInterval(Date start, Date end) {
        this.queue.offer(new TimePoint(start, true));
        this.queue.offer(new TimePoint(end, false));
    }

    /**
     * method adds new interval to be processed
     * @param interval TimeInterval.
     */
    public void pushInterval(TimeInterval interval) {
        pushInterval(interval.getStart(), interval.getEnd());
    }

    /**
     * method clear precession queue.
     * So after calling the method object will be reloaded.
     */
    public void clear() {
        this.queue.clear();
    }

    /**
     * method returns interval, which contains
     * maximun number of connections.
     * @return MaxLoadedInterval.
     */
    public MaxLoadedInterval getMaxLoadedInterval() {
        Date start = null;
        Date end = null;
        int max = 0;
        int count = 0;

        while (!this.queue.isEmpty()){
            TimePoint timePoint = this.queue.poll();
            if (timePoint.isStart()) {
                count++;
                if (count > max) {
                    max = count;
                    start = timePoint.getTimeValue();
                }
            } else {
                if (count == max) {
                    end = timePoint.getTimeValue();
                }
                count--;
            }
        }

        return new MaxLoadedInterval(start, end, max);
    }

    /**
     * class was created for separation
     * starting and ending points in processing queue
     */
    private static class TimePoint implements Comparable<TimePoint> {
        private final Date timeValue;
        //true if point is start of the interval, otherwise false
        private final boolean isStart;

        /**
         * default constructor.
         * @param timeValue Date.
         * @param isStart boolean.
         */
        public TimePoint(Date timeValue, boolean isStart) {
            this.timeValue = timeValue;
            this.isStart = isStart;
        }


        public Date getTimeValue() {
            return timeValue;
        }

        public boolean isStart() {
            return isStart;
        }

        @Override
        public int compareTo(TimePoint o) {
            return this.timeValue.compareTo(o.getTimeValue());
        }
    }

    /**
     * class implements closed time interval,
     * which contains start time and end time.
     */
    public static class TimeInterval {
        private final Date start;
        private final Date end;


        public TimeInterval(Date start, Date end) {
            if (start.compareTo(end) > 0) {
                throw new RuntimeException("Start time could not be greater than end time!");
            }
            this.start = start;
            this.end = end;
        }

        public Date getStart() {
            return start;
        }

        public Date getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeInterval that = (TimeInterval) o;

            if (!start.equals(that.start)) return false;
            return end.equals(that.end);
        }

        @Override
        public int hashCode() {
            int result = start.hashCode();
            result = 31 * result + end.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "TimeInterval{" +
                    "start=" + start.getTime() +
                    ", end=" + end.getTime() +
                    '}';
        }
    }

    /**
     * interval of time, when maximum client connection appear.
     * In comparing to parent class has addition int field -  maxIntervals,
     * which contains number of client connections.
     */
    public static class MaxLoadedInterval extends TimeInterval {
        private final int maxIntervals;

        public MaxLoadedInterval(Date start, Date end, int maxIntervals) {
            super(start, end);
            this.maxIntervals = maxIntervals;
        }


        public int getMaxIntervals() {
            return maxIntervals;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MaxLoadedInterval that = (MaxLoadedInterval) o;

            return maxIntervals == that.maxIntervals;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result
                    = 31 * result + maxIntervals;
            return result;
        }

        @Override
        public String toString() {
            return super.toString() +
                    "maxIntervals=" + maxIntervals;
        }
    }
}
