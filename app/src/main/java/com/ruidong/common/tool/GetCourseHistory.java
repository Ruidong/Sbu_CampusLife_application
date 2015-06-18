//package com.ruidong.common.tool;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
//import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
//import com.gargoylesoftware.htmlunit.html.HtmlInput;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.html.HtmlTable;
//import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
//import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
//
//public class GetCourseHistory {
//
//	public HtmlTable getCourseHistory(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
//		// Create a webclient
//		WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//		// Decide the support of options, like css, js
//		webClient.getOptions().setJavaScriptEnabled(true);
//		webClient.getOptions().setCssEnabled(false);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
//		/* ====================================================================
//		 * The first page is the web page of the login
//		 * Simulating the process that input the username and password
//		 * then, click the button "Sign in"
//		 */
//		HtmlPage page = webClient.getPage(Parameter.SOLAR_URL);
//		HtmlInput uname = (HtmlInput)page.getHtmlElementById("userid");
//		HtmlInput pwd   = (HtmlInput)page.getHtmlElementById("pwd");
//		uname.setValueAttribute(username);
//		pwd.setValueAttribute(password);
//		HtmlInput sighIn = (HtmlInput)page.getElementByName("Submit");
//
//		/*	====================================================================
//		 *  After clicking "Sigh in" button,
//		 *  we enter the main web page of solar system, calling it as "page2"
//		 *  Then, we want to simulate clicking the link "Academic Records"
//		 *  to get a new page called "page3"
//		 */
//		HtmlPage page2 = sighIn.click();
//		HtmlAnchor anchor1 = page2.getAnchorByText(Parameter.ACADEMIC_RECORDS);
//		HtmlPage page3 = anchor1.click();
//
//		/*	====================================================================
//		 *  In page3, we should simulate clicking the link "My Course History"
//		 *  The real link of the link is in the frame whose id = ptifrmtgtframe,
//		 *  so first, we should find the frame,
//		 *  and get the dynamic code in the frame, called "framePage1"
//		 *  then, find the real link of "My Course History"
//		 *  and click it, we can go to the new webpage called "page4"
//		 */
//		HtmlPage framePage1 = (HtmlPage)page3.getFrameByName(Parameter.FRAME_NAME).getEnclosedPage();
//		HtmlAnchor anchor2 = framePage1.getAnchorByText(Parameter.COURSE_HISTORY);
//		HtmlPage page4 = anchor2.click();
//
//		/*	=====================================================================
//		 * 	Page4 has the same situation as page3,
//		 * 	so we should find the frame which contains the content,
//		 *  this frame we call it as "framePage2".
//		 *  After find the frame, we should find the corresponding table
//		 *  which contains the information of course history
//		 */
//		HtmlPage framePage2 = (HtmlPage)page4.getFrameByName(Parameter.FRAME_NAME).getEnclosedPage();
//		final HtmlTable table = framePage2.getHtmlElementById("CRSE_HIST$scroll$0");
//		return table;
//	}
//
//	/*
//	 * This method will return a JSON array,
//	 * so that in Android client it will be more convenient
//	 * to process such data structure
//	 */
//	public static JSONObject getJsonObject(HtmlTable table) throws IndexOutOfBoundsException, JSONException{
//		JSONArray ja = new JSONArray();
//		for(int i=1; i<table.getRowCount(); i++){
//			JSONObject jo = new JSONObject();
//			jo.put("Course", table.getRow(i).getCell(0).asText());
//			jo.put("Description", table.getRow(i).getCell(1).asText());
//			jo.put("Term", table.getRow(i).getCell(2).asText());
//			jo.put("Grade", table.getRow(i).getCell(3).asText());
//			jo.put("Units", table.getRow(i).getCell(4).asText());
//			ja.put(jo);
//		}
//		JSONObject ob = new JSONObject();
//		ob.put("Course History", ja);
//		return ob;
//	}
//
//	public JSONArray getJsonArray(){
//		JSONObject jsonObject = new JSONObject();
//		JSONArray array = new JSONArray();
//		try {
//			 jsonObject = getJsonObject(getCourseHistory("109905574", "WOSHIZRD123"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			array = (JSONArray) jsonObject.get("Course History");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return array;
//	}
//
//
//
////	// Test
////	public static void main(String[] args){
////		GetCourseHistory gth = new GetCourseHistory();
////		try {
////			System.out.println(getJsonObject(gth.getCourseHistory("110014966", "xxjispig123")));
////		} catch (FailingHttpStatusCodeException | IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IndexOutOfBoundsException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (JSONException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//}
