/**
 * @Name: Apurva Naryaan
 * @Date: 17 June 2014
 */
package com.example.ivrapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

public class TraverseTreeActivity extends ListActivity {
 
//	http://129.97.172.206/get_nodes_children.php?node_id=16
	
	// Search EditText
  //  EditText inputSearch;
   
    
   
	private static final String TAG_SUCCESS = "success";
	
	private static final String TAG_NODES = "nodes";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_SPECIAL = "special";
    private static final String TAG_PARENT = "piid";
    private static final String TAG_DESC = "description";	
	// Progress Dialog
   // private ProgressDialog pDialog;
   // private ListView listView;
	
    // private ProgressBar spinner;
    
    
    
    JSONArray nodes;
	ArrayList<HashMap<String, String>> nodesList;
	
	String phoneStr = "";
	String specialStr = "";
	String finalStr = "";
	String temp_for_back="";
	String temp_node_id_back="";
	String current_node_parent ="";
	String flag_call = "";

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_view);
		
	    
		//inputSearch = (EditText) findViewById(R.id.inputSearch);
		new TraverseTree().execute("2");
		
		
		
		}
	/**
     * Background Async Task to Get complete product details
     * */
	
    
    class TraverseTree extends AsyncTask<String, String, String> {
 
    	int success;
    	boolean isLeaf;
    	
        /**
         * Before starting background thread Show Progress Dialog
         * */
    	
        @Override
        protected void onPreExecute() {
        	
            super.onPreExecute();
        }
 
        /**
         * Getting product details in background thread
         * */
        
        protected String doInBackground(String... params) {
        	
        	Log.d("passed in param: ", params[0]);
        	
    		// Creating HTTP client
    		HttpClient httpClient = new DefaultHttpClient();
    		 
    		// Creating HTTP Post
    		HttpGet request = new HttpGet("http://www.ivrcrasher.com/get_nodes_children.php?node_id=" + params[0]);
    		//Sending Data to the server
    		//HttpClient httpclient1 = new DefaultHttpClient();
            //HttpPost httppost1 = new HttpPost("http://www.ivrcrasher.com/receive_data.php");
    		
    		
    		//--------------------------------------------------------------------------------------------
    		// main code block
    		//--------------------------------------------------------------------------------------------
    		try {
    		    HttpResponse response = httpClient.execute(request);
    		 
    		    // writing response to log
    		    Log.d("Http Response:", response.toString());
//    		    Log.d("content", convertStreamToString(response.getEntity().getContent()) );
    		    JSONObject json = new JSONObject(convertStreamToString(response.getEntity().getContent()));
    		    Log.d("content", json.toString());

    		    success = json.getInt(TAG_SUCCESS);
    		    
    		    if (success == 1){
    		    	
	    		    nodes = json.getJSONArray(TAG_NODES);
	    		    
	    		    nodesList = new ArrayList<HashMap<String, String>>();
	    		
	    		    if (nodes.length() > 1){
	    		    	isLeaf = false;
	    		    	//For obtaining the node of the parent for going back
	    		    	JSONObject p = nodes.getJSONObject(0);
	    		    	current_node_parent = p.getString(TAG_PARENT);
	    		    	Log.d("parent straight from json", current_node_parent);
	    		    	// End of obtaining the node of the parent for going back
	    		    	 for(int i = 1; i < nodes.length(); i++){
		                    JSONObject c = nodes.getJSONObject(i);
		                    
		                    // Storing each json item in variable
		                    String id = c.getString(TAG_ID);
		                    String name = c.getString(TAG_NAME);
		                    String phone = c.getString(TAG_PHONE);
		                    String special = c.getString(TAG_SPECIAL);
		                    String piid = c.getString(TAG_PARENT);
		                    String description = c.getString(TAG_DESC);
		                    
		                    if(description == "Leaf")
		                    {	
		                    	
		                    }
		                 
		                   		                    	                    		                    
		                    // creating new HashMap
		                    HashMap<String, String> map = new HashMap<String, String>();
		                     
		                    // adding each child node to HashMap key => value
		                    
		                    map.put(TAG_ID, id);
		                    map.put(TAG_NAME, name);
		                    map.put(TAG_PHONE, phone);
		                    map.put(TAG_SPECIAL, special);
		                    map.put(TAG_PARENT, piid);
		                    map.put(TAG_DESC, description);
		                    
		                    // adding HashList to ArrayList
		                    nodesList.add(map);
		                 	                   
		                    
		                }
	    		    	// Sorting the Hashmap to display in alphabetical order
	    		    	    Collections.sort(nodesList, new Comparator<HashMap< String,String >>() {

	    		    	        @Override
	    		    	        public int compare(HashMap<String, String> lhs,
	    		    	                HashMap<String, String> rhs) {
	    		    	        	String firstValue = lhs.get(TAG_NAME);
	    		    	            String secondValue = rhs.get(TAG_NAME);
	    		    	            return firstValue.compareTo(secondValue);
	    		    	            // Do your comparison logic here and retrn accordingly.
	    		    	            //return 0;
	    		    	        }
	    		    	    }); 
	    		    }
	    		    else{
	    		    	isLeaf = true;
	    		    	
	    		    }
	                
	    		  
	                Log.d("list content", nodesList.toString());
    		    }
    		    
    		} 
    		
    		//--------------------------------------------------------------------------------------------
    		//--------------------------------------------------------------------------------------------
    		//--------------------------------------------------------------------------------------------
    		
    		catch (ClientProtocolException e) {
    		    // writing exception to log
    		    e.printStackTrace();
    		         
    		} 
    		catch (IOException e) {
    		    // writing exception to log
    		    e.printStackTrace();
    		}
    		catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

    		
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
        	
        	
            ListAdapter adapter = new SimpleAdapter(
            		TraverseTreeActivity.this, 
            		nodesList,
                    R.layout.list_item,
                    new String[] {TAG_ID, TAG_NAME, TAG_PHONE, TAG_SPECIAL, TAG_PARENT,TAG_DESC }, 
             		new int[] {R.id.id, R.id.name, R.id.phone, R.id.special, R.id.piid, R.id.description}
            			
            		);
      
           
            
           
            setListAdapter(adapter);
            
          
            
            
            
            // selecting single ListView item
            ListView lv = getListView();
            
    
            // Launching new screen on Selecting Single ListItem
            lv.setOnItemClickListener(new OnItemClickListener() {
        
                
				@Override
				
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                	
                	
                	
                	
                	
                	
                	String node_id = ((TextView) view.findViewById(R.id.id)).getText().toString();
                	String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                	String phone = ((TextView) view.findViewById(R.id.phone)).getText().toString();
                	String special = ((TextView) view.findViewById(R.id.special)).getText().toString();
                	String piid = ((TextView) view.findViewById(R.id.piid)).getText().toString();
                	String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
                	
                	//Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
                	temp_for_back = special;
                	
                	               	
                	if (phone.length() > 0)
                		phoneStr = phone;
                	if (special.length() > 0)
                		specialStr = specialStr +  special;
                	
                	if(description.equals("UI"))
                	{
                		AlertDialog alert = new AlertDialog.Builder(TraverseTreeActivity.this).create();

                		alert.setTitle("Title");
                		alert.setMessage("Message");

                		// Set an EditText view to get user input 
                		final EditText input = new EditText(TraverseTreeActivity.this);
                		alert.setView(input);

                		alert.setButton("Ok", new DialogInterface.OnClickListener() {
                		public void onClick(DialogInterface dialog, int whichButton) {
                		  Editable value = input.getText();
                		  // Do something with value!
                		  }
                		});

                		/*alert.setButton("Cancel", new DialogInterface.OnClickListener() {
                		  public void onClick(DialogInterface dialog, int whichButton) {
                		    // Canceled.
                		  }
                		});*/

                		alert.show();
                		
                		
                	}
                	
                	if(description.equals("Leaf"))               		
                	{           	
                		
                		
                					finalStr = phoneStr + specialStr;
                				//	Toast.makeText(getApplicationContext(), phoneStr + "" + specialStr, Toast.LENGTH_SHORT).show();
                					
                					
                					finish();
                					//Sending Data to Server////////////////////////////
                					// HttpClient
                					
/////////////////////////////////////////////////////////////////////////////////////////////////////
                		            Uri number = Uri.parse("tel:"+finalStr);
               					
                					Intent dial = new Intent(android.content.Intent.ACTION_CALL,number);
                				    
                					startActivity(dial);
                				    
                	}
                	Log.d( "tv",  node_id + " " + name  );
                //	Toast.makeText(getApplicationContext(), specialStr, Toast.LENGTH_SHORT).show();
                	
                	Button backBtn = (Button) findViewById(R.id.backBtn);
                	backBtn.setOnClickListener(new View.OnClickListener() {
                		 
                        @Override
                        public void onClick(View view) {
                        	//Toast.makeText(getApplicationContext(), specialStr, Toast.LENGTH_SHORT).show();	                       	                       	
                        	Log.e("Parent Node ID",  current_node_parent);
                        	new TraverseTree().execute(current_node_parent); 
                        	if(specialStr.length()-temp_for_back.length() >= 0 ){
                            	specialStr = specialStr.substring(0,(specialStr.length()- temp_for_back.length()));
                            	}
                            	else
                            	{	
                            		specialStr = "";
                            	}
                        	//Toast.makeText(getApplicationContext(), specialStr, Toast.LENGTH_SHORT).show();	
                        	new TraverseTree().execute(current_node_parent); 
                        	
                             }
                	});
                	Button homeButton = (Button) findViewById(R.id.home);
                	homeButton.setOnClickListener(new View.OnClickListener() {
                		 
                        @Override
                        public void onClick(View view) {
                        	//Toast.makeText(getApplicationContext(), specialStr, Toast.LENGTH_SHORT).show();	                       	                       	
                        	Log.e("Parent Node ID",  current_node_parent);
                        	specialStr = "";
                        	new TraverseTree().execute("2");
                        }
                        });
                	new TraverseTree().execute(node_id);
                	

                }
            });

            
        }
        
        
    //--------------------------------------------------------------------------------------------
	// end of AsyncTask
	//--------------------------------------------------------------------------------------------
   
    } 	
    //===================================================================================================
    //================================== Utility Functions ==============================================
    //===================================================================================================
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
   

}
