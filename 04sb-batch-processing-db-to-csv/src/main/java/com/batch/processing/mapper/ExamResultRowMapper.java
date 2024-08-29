package com.batch.processing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.batch.processing.model.ExamResult;

public class ExamResultRowMapper implements RowMapper<ExamResult> {

	@Override
	public ExamResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ExamResult(
				rs.getLong(1), 
				rs.getDate(2), 
				rs.getString(3), 
				rs.getString(4), 
				rs.getString(5), 
				rs.getDouble(6), 
				rs.getString(7), 
				rs.getInt(8)
				);
	}

}
