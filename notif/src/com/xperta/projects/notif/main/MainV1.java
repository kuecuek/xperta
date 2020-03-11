package com.xperta.projects.notif.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainV1 {
	
	public static final int 	COUNT=4;
	public static final String 	LOCATION="ALL";
	
	public static void main(String[] args) {
		try {
			List temp= new ArrayList();
			List<Line> lines = new ArrayList<Line>();
			Map<String, List> map =  new HashMap<String, List>();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			String output = JavaRunCommand.getConsoleOutputFromPython();
			output = output.replaceAll("�?lksel", ""); //Ãƒ?lksel		(cmd)				Ã�lksel => eclipse
			
			output = output.split("                                  --------------")[1];
			
			String[] data = output.split("                ");
			
			for(int i=0; i<data.length; i++ ) {
				if( !"".equals(data[i]) && !data[i].trim().startsWith("REVIZE")  ) {
					String g = data[i].trim().replaceAll("  ", " ");
					temp.add(g);
				}
			}

			for(int i=0; i<temp.size(); i++ ) {
				try {
					String[] sArray =  String.valueOf(temp.get(i)).split("  ");
//					System.out.println( sArray[0].toString().trim() );
//					System.out.println( sArray[5].toString().trim() );
					
					Line line = new Line();
					
					if(sArray.length > 2) {
						Date occured =  sdf.parse(sArray[0].toString().trim());
						String place =  sArray[5].toString().trim();
					
						if( DateUtils.isSameDay(occured, new Date()) ) {
							if(map.containsKey(place))
								map.get(place).add(occured);
							else {
								List l = new ArrayList();
								l.add(occured);
								map.put(place, l);
							}
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			StringBuffer sb = new StringBuffer();
			Iterator it = map.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry pair = (Map.Entry)it.next();
		    	if( LOCATION.equalsIgnoreCase( pair.getKey().toString() ) && ((List)pair.getValue()).size() > COUNT ) {
			    	System.out.println( String.format("%-45s: %2$s", pair.getKey(), ((List)pair.getValue()).size()) );			        
		    	} else if("ALL".equalsIgnoreCase( LOCATION ) && ((List)pair.getValue()).size() > COUNT ) {
		    		System.out.println( String.format("%-45s: %2$s", pair.getKey(), ((List)pair.getValue()).size()) );
		    		sb.append( String.format("%-45s: %2$s", pair.getKey(), ((List)pair.getValue()).size())).append("\n");
		    	}
		    	it.remove(); // avoids a ConcurrentModificationException
		    }
		    
		    String from 	= "bcemkucuk@gmail.com";
		    String to 		= "bcemkucuk@gmail.com";
		    String cc 		= "tolgamm@hotmail.com;taycel@gmail.com;batur.rmzn@gmail.com"; //bkasal92@gmail.com
		    String subject	= "Deprem Uyarısı (" + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ")";
		    
		    
		    
		    MGSample.sendSimpleMessage( from, to, /*cc,*/ subject, sb.toString() );

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
