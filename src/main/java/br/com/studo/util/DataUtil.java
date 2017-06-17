package br.com.studo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

	// converte uma String para sql.Date
	public static java.sql.Date formatDate(String data) throws ParseException {
		Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		java.sql.Date dataFormatada = new java.sql.Date(dt.getTime());
		return dataFormatada;
	}

	// Retonar apenas a data atual formatada
	public static java.sql.Date dataAtualFormatada() {
		java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.format(d);
		return d;
	}

	public static Date dataAtual() {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.format(d);
		return d;
	}

	// converte um sql.Date para String
	public static String formatDateForString(java.sql.Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String data = String.valueOf(format.format(date.getTime()));
		return data;
	}

	// converte um sql.Date para util.Date
	public static Date formatUtilDate(java.sql.Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data = (Date) date;
		format.format(data);
		return data;
	}

	public static java.sql.Date formatDateForSQL(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.sql.Date dataFormatada = new java.sql.Date(data.getTime());
		format.format(dataFormatada);
		return dataFormatada;
	}

	// Retona o ano atual em forma de String
	public static String anoAtual() {
		Calendar dt = Calendar.getInstance();
		String ano = String.valueOf(dt.get(Calendar.YEAR));
		return ano;
	}

	public static boolean validarData(java.sql.Date data) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		cal1.setTime(data);
		if (cal1.before(cal2)) {
			return false;
		}
		return true;
	}

}
