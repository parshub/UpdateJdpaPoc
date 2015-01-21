package com.jdpa.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.jdpa.utilities.DBUtilities;

public class ReportData {
	String surveyTrackTest;
	String testSurveyName;
	String expectedData;
	int status ;
	String  testStep ;
	
	List<String> actualData = new ArrayList<String>();
	List<String> listquetionId = new ArrayList<String>();
	List<List<String>> listResponce = new ArrayList<List<String>>();
	long totalTimeDuratioOfOneTestStep ;
	
	String tempStatus = "Not Verfied";
	String tempActual = null;

	JSONArray objJsonArray = new JSONArray();
	File file =new File("/jdpa.coreframwork.web/Report.json");
	
	
	public ReportData() {

	}

	public void setSurveyTrackTest(String surveyTrackTest) {
		this.surveyTrackTest = surveyTrackTest;
	}

	public void setTestSurveyName(String testSurveyName) {
		this.testSurveyName = testSurveyName;
	}

	public void setTestStep(String testStep){
		this.testStep = testStep;
	}

	public void setQuetionID(List<String> listquetionId) {
		this.listquetionId = listquetionId;
	}

	public void setResponse(List<List<String>> listResponce) {
		this.listResponce = listResponce;
	}

	public void setExpectedData(String expectedData) {
		this.expectedData = expectedData;
	}

	public void setActualData(List<String> actualData) {
		this.actualData = actualData;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setTotalTimeDuratioOfOneTestStep(long totalTimeDuratioOfOneTestStep) {
		this.totalTimeDuratioOfOneTestStep = totalTimeDuratioOfOneTestStep;
	}
	
	public String getSurveyTrackTest() {
		return surveyTrackTest;
	}

	public String getTestSurveyName() {
		return testSurveyName;
	}

	public String getTestStep() {
		return testStep;
	}

	public List<String> getQuetionID() {
		return listquetionId;
	}

	public List<List<String>> getResponse() {
		return listResponce;
	}

	public String getExpectedData() {
		return expectedData;
	}

	public List<String> getActualData() {
		return actualData;
	}

	public int getStatus() {
		return status;
	}

	public long getTotalTimeDuratioOfOneTestStep() {
		return totalTimeDuratioOfOneTestStep;
	}

	
	public void createJsonFileForTestReoprt() throws IOException 
	{
		//FileWriter fileWritter = new FileWriter(file.getName(),true);
		//BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		DBUtilities objDBUtilities = new DBUtilities();
		JSONObject finalJsonObj = new JSONObject();
		String collectionName = "Report";
		if(listquetionId.size()!=0)
		{	
			for (int i = 0; i < listquetionId.size(); i++)
			{
				JSONObject tempJsonObj = new JSONObject();
				if(status == 1)
					tempStatus = "Passed";
				else if(status == 2)
					tempStatus = "Failed" ;
				else 
					tempStatus = "Skipped" ;
				surveyTrackTest = tempStatus;
				tempJsonObj.put("surveyTrackTest ",surveyTrackTest);
				tempJsonObj.put("Expected ","Verified" );
				tempJsonObj.put("Status ", tempStatus);
				tempJsonObj.put("Questio ID ", listquetionId.get(i));
				tempJsonObj.put("listResponce",  listResponce.get(i));
				if(actualData.get(i).equals("true"))
					tempActual = "Verified";
				tempJsonObj.put("Actual ", tempActual);
				objJsonArray.add(tempJsonObj);
			}
			finalJsonObj.put("Time Taken By Current Step", totalTimeDuratioOfOneTestStep);
			finalJsonObj.put("surveyTrackTest ",surveyTrackTest);
			finalJsonObj.put("testSurveyName ", "TraversalPath.json");
			finalJsonObj.put("testStep ",testStep);
			finalJsonObj.put(testStep, objJsonArray);
			objDBUtilities.saveData(collectionName,finalJsonObj);
		}
		else {
			JSONObject objJson = new JSONObject();

			if(status == 1)
				tempStatus = "Passed";
			else if(status == 2)
				tempStatus = "Failed" ;
			else 
				tempStatus = "Skipped" ;
			surveyTrackTest = tempStatus;

			objJson.put("Time Taken By Current Step", totalTimeDuratioOfOneTestStep);
			objJson.put("surveyTrackTest ",surveyTrackTest);
			objJson.put("testSurveyName ", "TraversalPath.json");
			objJson.put("testStep ",testStep);
			objJson.put("Expected ","Verified" );
			objJson.put("Status ", tempStatus);
			objJsonArray.add(objJson);
			finalJsonObj.put(testStep, objJsonArray);
			objDBUtilities.saveData(collectionName , finalJsonObj);
		}
		//bufferWritter.write(finalJsonObj.toJSONString());
		//bufferWritter.close();
	}
}




//JSONObject objJson = new JSONObject();

/*if(status == 1)
	tempStatus = "Passed";
else if(status == 2)
	tempStatus = "Failed" ;
else 
	tempStatus = "Skipped" ;
surveyTrackTest = tempStatus;

objJson.put("surveyTrackTest ",surveyTrackTest);
objJson.put("testSurveyName ", "TraversalPath.json");
objJson.put("testStep ",testStep);
objJson.put("Expected ","Verified" );
objJson.put("Status ", tempStatus);*/
