package com.polybrain.util;

import com.polybrain.service.impl.BookingChooSerImpl;
import com.polybrain.service.BookingChooseService;
import com.polybrain.service.BookingService;
import com.polybrain.service.impl.BookingServiceImpl;

public class BookConstants {
    public static final BookingService SERVICE = new BookingServiceImpl();
    public static final BookingChooseService SERVICETable = new BookingChooSerImpl();
}
