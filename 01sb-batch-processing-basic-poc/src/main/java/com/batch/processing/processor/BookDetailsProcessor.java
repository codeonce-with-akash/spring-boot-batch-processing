package com.batch.processing.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("bdProcessor")
public class BookDetailsProcessor implements ItemProcessor<String, String> {

	public BookDetailsProcessor() {
		System.out.println("BOOKDETAILSPROCESSOR :: 0-PARAM CONSTRUCTOR");
	}

	@Override
	public String process(String item) throws Exception {
		System.out.println("BookDetailsProcessor.process()");
		String bookWithTitle = null;
		if (item.equalsIgnoreCase("CRJ"))
			bookWithTitle = item + "BY HS AND PN";
		else if (item.equalsIgnoreCase("TIJ"))
			bookWithTitle = item + "BY BE";
		else if (item.equalsIgnoreCase("HFJ"))
			bookWithTitle = item + "BY KS";
		else if (item.equalsIgnoreCase("EJ"))
			bookWithTitle = item + "BY JB";
		else if (item.equalsIgnoreCase("BBJ"))
			bookWithTitle = item + "BY RNR";
		else if(item.equalsIgnoreCase("SBMS"))
			bookWithTitle = item + "BY RAGHU";
		return bookWithTitle;
	}

}
