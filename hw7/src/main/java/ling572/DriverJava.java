package ling572;

import java.io.*;

public class DriverJava {
	public static void main(String args[]) {
		SupportVectorFile file = new SupportVectorFile();
		file.read(new File("/Users/m048871/Dropbox/CLMS/Courses/LING 572/Homework/hw7/q1/model.1"));
		System.out.println();
	}

}
