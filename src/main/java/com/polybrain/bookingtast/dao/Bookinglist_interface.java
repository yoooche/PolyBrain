package com.polybrain.bookingtast.dao;


import com.polybrain.bookingtast.entity.BookinglistVO;
import com.polybrain.bookingtast.entity.TablebookingVO;

import java.util.List;

public interface Bookinglist_interface extends com.polybrain.common.dao.CoreDao<TablebookingVO, Integer>{

	public void insert(BookinglistVO bookinglistVO);
    public void update(BookinglistVO bookinglistVO);

    public BookinglistVO findByPrimaryKey(Integer BOOKING_NO);
    public List<BookinglistVO> getAll();
    
}
