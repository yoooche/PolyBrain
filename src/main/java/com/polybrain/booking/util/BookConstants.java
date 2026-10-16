package com.polybrain.booking.util;

import com.polybrain.booking.service.impl.BookingChooSerImpl;
import com.polybrain.booking.service.BookingChooseService;
import com.polybrain.booking.service.BookingService;
import com.polybrain.booking.service.impl.BookingServiceImpl;

public class BookConstants {
    public static final BookingService SERVICE = new BookingServiceImpl();
    public static final BookingChooseService SERVICETable = new BookingChooSerImpl();
}
