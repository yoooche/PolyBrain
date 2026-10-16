package com.polybrain.repository;


import com.polybrain.model.BookinglistTO;
import com.polybrain.model.AdminTablebookingTO;

import java.util.List;

public interface Bookinglist_interface extends com.polybrain.repository.CoreDao<AdminTablebookingTO, Integer>{

	public void insert(BookinglistTO bookinglistVO);
    public void update(BookinglistTO bookinglistVO);

    public BookinglistTO findByPrimaryKey(Integer BOOKING_NO);
    public List<BookinglistTO> getAll();
    
}
