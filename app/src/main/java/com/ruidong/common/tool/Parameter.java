package com.ruidong.common.tool;

/*
 * This file contains all necessary parameters we need when the APP doing data crawling. 
 * Parameters list:
 * Anchor, frame, URL.
 */
public class Parameter {
	// Address of Solar system
	final static String SOLAR_URL = "https://psns.cc.stonybrook.edu/psp/he90prods/";
	
	// List of Anchor
	final static String ENROLLMENT = "Enrollment";
	final static String ACADEMIC_RECORDS = "Academic Records";
	final static String VIEW_TRANSCRIPT = "View My Grades";
	final static String COURSE_HISTORY = "My Course History";
	final static String WEEKLY_SCHEDULE = "My Weekly Schedule";
	
	// List of Frame
	final static String FRAME_NAME = "TargetContent";
}
