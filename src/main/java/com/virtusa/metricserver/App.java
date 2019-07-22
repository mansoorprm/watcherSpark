package com.virtusa.metricserver;

import static spark.Spark.get;
import java.util.Date;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class App 
{
	static Logger log = Logger.getLogger(App.class.getName());
	static MainThread t1 = null;
	static Date startTime ;
	
    public static void main( String[] args )
    {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	
    	
    	
    	startServer();
        get("/start", (req, res) -> 
         gson.toJson(startServer()));
         
        get("/status", (req, res) -> gson.toJson(   serverStatus()));
        get("/stop", (req, res) -> gson.toJson(stopServer()));
        
        
       // get("/stop", (req, res) -> stopServer());
       // get("/status", (req, res) -> serverStatus());
     }
    
    
    private static Serverstatus serverStatus() {
	 	Serverstatus st = new Serverstatus();
	 	st.setStartTime("");
    	st.setUpTime("");
    	try {
    		
    		
    		if (t1.exit) {
    			st.setStatus("Not Running");
    		}else {
	    		long difference = new Date().getTime() - startTime.getTime();
	    		System.out.println(difference/1000);
	    		st.setStatus("Running");
	    		st.setStartTime(startTime.toString());
	    		st.setUpTime(String.valueOf(difference/1000) );
	    	}
    		
    		return st;
	    		//return "Server Running since " + difference/1000 + " seconds";
	    }catch(Exception e) {
	    	
	    	st.setStatus("Not Running");
	   		return st;
	    }
    }
    
   
    
    private static Serverstatus stopServer() {
    	Serverstatus st = new Serverstatus();
    	st.setStartTime("");
    	st.setUpTime("");
    	 try { 
             Thread.sleep(500); 
             t1.stop(); // stopping thread t1 
          //   t2.stop(); // stopping thread t2 
             Thread.sleep(500); 
         }catch(NullPointerException NE) {
        	 st.setStatus("Not Running");
	    	return st;
    	 }catch (Exception e) { 
             System.out.println("Caught:" + e); 
         } 
         System.out.println("Exiting the main Thread"); 
         
        
         st.setStatus("Stopped");
    	return st;
    }
    
    private static Serverstatus startServer() {
    	Serverstatus st = new Serverstatus();
    	st.setStartTime("");
    	st.setUpTime("");
    	try {
	    	if (t1.exit) {
	    		startTime = new Date();
	    		t1 = new MainThread("First  thread");   
	    	}
	    	else {
	    		st.setStatus("Already Running");
	             return st;
	    	}
    	}catch(NullPointerException NPE) {
    		startTime = new Date();
    		t1 = new MainThread("First  thread"); 
    	}
    	catch(Exception e) {
    		st.setStatus(e.toString());
    		System.out.print(e);
    	}
    	st.setStatus("Started");
    	return st;
    }
    
    
	/*
	 * private static String serverStatus() { StringBuffer sb = new StringBuffer();
	 * try {
	 * 
	 * sb.append("{"); if (t1.exit) { sb.append("status" + ":" + "Not Running");
	 * sb.append("}"); }else { long difference = new Date().getTime() -
	 * startTime.getTime(); System.out.println(difference/1000); sb.append("status"
	 * + ":" + "Running"); sb.append(","); sb.append("startTime" + ":" + startTime);
	 * sb.append(","); sb.append("upTime" + ":" + difference/1000 + " seconds");
	 * sb.append("}"); }
	 * 
	 * return sb.toString(); //return "Server Running since " + difference/1000 +
	 * " seconds"; }catch(Exception e) {
	 * 
	 * sb.append("status" + ":" + "Not Running"); sb.append("}"); return
	 * sb.toString(); } }
	 * 
	 * 
	 * 
	 * private static String stopServer() { StringBuffer sb = new StringBuffer();
	 * try { Thread.sleep(500); t1.stop(); // stopping thread t1 // t2.stop(); //
	 * stopping thread t2 Thread.sleep(500); }catch(NullPointerException NE) {
	 * sb.append("{"); sb.append("status"+":"+ " Not Running"); sb.append("}");
	 * return sb.toString(); }catch (Exception e) { System.out.println("Caught:" +
	 * e); } System.out.println("Exiting the main Thread");
	 * 
	 * 
	 * sb.append("{"); sb.append("status"+":"+ " Stopped"); sb.append("}"); return
	 * sb.toString(); }
	 * 
	 * private static String startServer() { StringBuffer sb = new StringBuffer();
	 * try { if (t1.exit) { startTime = new Date(); t1 = new
	 * MainThread("First  thread"); } else { sb.append("{"); sb.append("status"+":"+
	 * " Already Running"); sb.append("}"); return sb.toString(); }
	 * }catch(NullPointerException NPE) { startTime = new Date(); t1 = new
	 * MainThread("First  thread"); } catch(Exception e) { sb.append("{");
	 * sb.append("status"+":"+ e); sb.append("}"); System.out.print(e); }
	 * sb.append("{"); sb.append("status"+":"+ " Started"); sb.append("}"); return
	 * sb.toString(); }
	 */
}
