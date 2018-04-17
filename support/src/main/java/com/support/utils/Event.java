package com.support.utils;

import com.squareup.otto.Bus;

/**
 * Created by kunal on 16/8/17.
 */

public class Event {

    private static Event event = new Event();
    public static Bus bus = event.getBus();

    private Event() {

    }

    public Bus getBus() {
        if (bus == null) {
            bus = new Bus();
        }
        return bus;
    }

}
