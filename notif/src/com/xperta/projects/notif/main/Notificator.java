package com.xperta.projects.notif.main;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mashape.unirest.http.exceptions.UnirestException;

public class Notificator {

	private final static String[] SEARCH_TEXTS = { "MARMARA", "SILIVRI", "TEKIRDAG" };	
	List<String> temp;
	Map<String, List<Date>> map;
	String[] sArray;
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	private static Date CHECKDATE;
	

	public Notificator() {
		try {
			CHECKDATE = sdf.parse("2020.03.11 09:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private String getDataFromKoeri() throws UnsupportedEncodingException {
		return JavaRunCommand.getConsoleOutputFromPython();
	}

	private Map<String, List<Date>> formatData(String data) {
		map = new HashMap<String, List<Date>>();
		temp = new ArrayList<String>();
		data = data.replaceAll("�?lksel", "").replaceAll("Ý", "").replaceAll("Otomatik", ""); // Ãƒ?lksel (cmd) Ã�lksel => eclipse
		
		String[] sArray = data.split("                                  --------------");
		if(sArray.length>0)
			data = sArray[1];		
		sArray = data.split("                ");
		for (int i = 0; i < sArray.length; i++) {
			if (!"".equals(sArray[i]) && !sArray[i].trim().startsWith("REVIZE")) {
				String g = sArray[i].trim().replaceAll("  ", " ");
				temp.add(g);
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			try {
				sArray = String.valueOf(temp.get(i)).split("  ");
				Line line = new Line();
				if(sArray.length > 5) {
					Date occured = sdf.parse(sArray[0].toString().trim());
					String place = sArray[5].toString().trim();
					if (occured.after( CHECKDATE )) {
						if (map.containsKey(place)) 
							map.get(place).add(occured);
						else {
							List l = new ArrayList();
							l.add(occured);
							map.put(place, l);
						}
					}
				}
			} catch (Exception e) {
//				e.printStackTrace();
//				System.exit(-1);
			}
		}
		return map;
	}

	private void sendMessage(String msg) throws UnirestException {
		String to 		= "bcemkucuk@gmail.com";
		String from 	= "info@xperta.com.tr";
		String cc 		= "bkasal92@gmail.com,tolgamm@hotmail.com,taycel@gmail.com"; // batur.rmzn@gmail.com
		String subject 	= "Deprem Uyarısı (" + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ")";

		EMail.send(from, to, cc, subject, msg);
	}

	private static String checkPlaces(Set places) {
		for (Object place : places) {
			for (int i = 0; i < SEARCH_TEXTS.length; i++) {
				if (String.valueOf(place).toUpperCase().contains(SEARCH_TEXTS[i])) {
					CHECKDATE = new Date();
					return String.valueOf(place);
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			Notificator notif = new Notificator();
			
			while (true) {
					System.out.print( "++ getting data from koeri:");
					String data = notif.getDataFromKoeri();
					System.out.println(" OK");
					Thread.sleep(2000);
					
					System.out.print( "++ data formatted:" );
					Map map = notif.formatData(data);
					System.out.println( " OK");
					
					Thread.sleep(2000);
					System.out.println(map.size());
		
					Set places = map.keySet();
					String place  = checkPlaces(places);
					if ( place != null) {
						notif.sendMessage("warning: new earthquake " + place);
					}

					// wait 5min for next execution ..
					Thread.sleep(300000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}