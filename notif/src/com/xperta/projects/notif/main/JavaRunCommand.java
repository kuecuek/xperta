package com.xperta.projects.notif.main;

import java.io.*;

public class JavaRunCommand {

    public static String getConsoleOutputFromPython() throws UnsupportedEncodingException {
        String output = null;
        StringBuffer result=new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec("python c:/tmp/notif/test.py");
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
//            System.out.println("Here is the standard output of the command:\n");
            while ((output = stdInput.readLine()) != null) {
            	result.append(output);
            }
            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):\n");
            while ((output = stdError.readLine()) != null) {
//                System.out.println(output);
            }
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
		return new String(result.toString().getBytes(), "UTF-8");
    }
}