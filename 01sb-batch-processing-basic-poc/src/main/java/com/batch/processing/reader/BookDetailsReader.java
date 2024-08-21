package com.batch.processing.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component("bdReader")
public class BookDetailsReader implements ItemReader<String> {

	String books[] = new String[] {"CRJ", "TIJ", "HFJ", "EJ", "BBJ", "SBMS"};
	Integer count = 0;
	String book = null;

	public BookDetailsReader() {
		System.out.println("BOOKDETAILSREADER :: 0-PARAM CONSTRUCTOR");
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		System.out.println("BookDetailsReader.read()");
		/**
		if (count < books.length) {
			book = books[count];
			count++;
			return book;
		}**/
		if(count<books.length)
			return books[count++];
		else
			return null;
	}

}
