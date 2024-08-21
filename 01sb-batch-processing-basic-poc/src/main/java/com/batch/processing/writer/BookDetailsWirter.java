package com.batch.processing.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("bdWriter")
public class BookDetailsWirter implements ItemWriter<String> {

	public BookDetailsWirter() {
		System.out.println("BOOKDETAILSWIRTER :: 0-PARAM CONSTRUCTOR)");
	}
	
	@Override
	public void write(List<? extends String> items) throws Exception {
		System.out.println("BookDetailsWirter.write()");
		items.forEach(System.out::println);
	}

}
