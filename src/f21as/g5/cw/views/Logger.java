package f21as.g5.cw.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private static String log = "This file has a log of sequential events followed by a complete report with earnings per item, total income, and menu items\n\n";
	
	static void updateLog(String text) {
		log += new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + " ==> ";
		log += text + "\n\n";
	}
	
	static void printLog(String report) {
		log += report;
		File logFile = new File("logFile.txt");
		try {
			if (logFile.createNewFile()) {
				FileWriter logFileWriter = new FileWriter(logFile);
				logFileWriter.write(log);
				logFileWriter.close();
			} else {
				logFile.delete();
//				logFile = new File("logFile.txt");
				logFile.createNewFile();
				FileWriter logFileWriter = new FileWriter(logFile);
				logFileWriter.write(log);
				logFileWriter.close();
			}
//			logFile.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(log);
	}
	
}
