package com.shopme.admin.exporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.User;

public class UserCsvExporter {

	public void export(List<User> listUser, HttpServletResponse response) throws IOException {
		DateFormat dateFormatter=new SimpleDateFormat("YYYY-MM-dd_HH-mm-ss");
		String timestamp=dateFormatter.format(new Date());
		String fileName="users_"+timestamp+".csv";
		response.setContentType("text/csv");
		String headerKey="Content-Disposition";
		String headerValue="application; filename="+fileName;
		response.setHeader(headerKey, headerValue);
		ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String [] csvHeader= {"User ID","Email","First Name","Last Name","Roles","Enabled"};
		String [] fieldMapping= {"id","email","firstName","lastName","roles","enabled"};
		csvWriter.writeHeader(csvHeader);
		for(User user:listUser) {
			csvWriter.write(user, fieldMapping);
		}
		csvWriter.close();
	}

}
