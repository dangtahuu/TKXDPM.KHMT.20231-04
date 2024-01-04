package subsystem;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import controller.UserSessionController;
import controller.user.pages.UserInvoiceController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Datasource;
import subsystem.config.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Interbank {

	 public static void main(String[] args) throws Exception {
	        // Create a Jetty server on port 8080
	        Server server = new Server(8080);

	        // Set up a simple handler to handle incoming requests
	        server.setHandler(new MyHandler());

	        // Start the server
	        server.start();
	        server.join(); // Wait for the server to finish
	    }

	    // Custom handler to process requests
	    private static class MyHandler extends AbstractHandler {
	        @Override
	        public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	            if (target.equals("/vnpay_return") && request.getMethod().equals("GET")) {
	                handleVnPayReturn(request, response, baseRequest);
	                
	            } else  if (target.equals("/vnpay_ipn") && request.getMethod().equals("GET")) {
	                handleVnPayIPN(request, response, baseRequest);
	         
	            } else {
	            	
	                response.setContentType("text/plain");
	                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                baseRequest.setHandled(true);
	                response.getWriter().println("404 Not Found");
	            }
	        }

	        private void handleVnPayIPN(HttpServletRequest request, HttpServletResponse response, org.eclipse.jetty.server.Request baseRequest) throws IOException {
	        	System.out.print("123");
	            // Your custom handling logic for /vnpay GET requests goes here
	            response.setContentType("text/plain");
	            response.setStatus(HttpServletResponse.SC_OK);
	            baseRequest.setHandled(true);
	            response.getWriter().write("Hello, this is the server response for a GET request at /vnpay!");
	        }
	        
	        private boolean handleVnPayReturn(HttpServletRequest request, HttpServletResponse response, org.eclipse.jetty.server.Request baseRequest) throws IOException {
	        	  Map fields = new HashMap();
	        	  int order_id = 0;
	        	    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
	        	    String fieldName = (String) params.nextElement();
	        	    String fieldValue = request.getParameter(fieldName);
	        	   
	        	    if ((fieldValue != null) && (fieldValue.length() > 0)) {
	        	        fields.put(fieldName, fieldValue);
	        	        if(fieldName.equals("vnp_TransactionNo"))
	        	        		order_id=Integer.parseInt(fieldValue);
	        	    }
	        	    }
	        	    
	        	    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
	        	    if (fields.containsKey("vnp_SecureHashType")) {
	        	    fields.remove("vnp_SecureHashType");
	        	    }
	        	    if (fields.containsKey("vnp_SecureHash")) {
	        	    fields.remove("vnp_SecureHash");
	        	    }
	        	    String signValue = Config.hashAllFields(fields);
	        	  
	        	  response.setContentType("text/plain");
	        	  
	        	
//	        	if (signValue.equals(vnp_SecureHash)) {
	        	    if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
	        	    	 response.setStatus(HttpServletResponse.SC_OK);
	     	            baseRequest.setHandled(true);
	     	            response.getWriter().write("Pay successfully!");
	     	           
	     	            UserInvoiceController userInvoiceController = new UserInvoiceController();
						userInvoiceController.handleSuccessInvoice(order_id);
						
					
		    	        
						return true;
	     	           
	     	           
	        	    } 
	        	    	
	        	    

//	        	} 
	        	else {
	        		 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	     	            baseRequest.setHandled(true);
	     	            response.getWriter().write("Something went wrong");
//	     	           UserInvoiceController userInvoiceController = new UserInvoiceController();
//						userInvoiceController.handleFailedInvoice();
	        	}
	            // Your custom handling logic for /vnpay GET requests goes here
	        	   
	        	    return false;
	           
	        }
	       
	    }
	    
	  
	 
	    
	    public void openPay(double fee, int order_id) throws IOException {
	        
	        try {
				String vnp_Version = "2.1.0";
				String vnp_Command = "pay";
				String orderType = "other";
				long amount = (long) (fee*100)+1000000;
				
				String vnp_TxnRef = Config.getRandomNumber(8);
				String vnp_IpAddr = "13.160.92.202";

				String vnp_TmnCode = Config.vnp_TmnCode;
				
				Map<String, String> vnp_Params = new HashMap<>();
				vnp_Params.put("vnp_Version", vnp_Version);
				vnp_Params.put("vnp_Command", vnp_Command);
				vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
				vnp_Params.put("vnp_Amount", String.valueOf(amount));
				vnp_Params.put("vnp_CurrCode", "VND");
				vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
				vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
				vnp_Params.put("vnp_OrderType", orderType);
				vnp_Params.put("vnp_Locale", "vn");
				vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
				vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

				Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				String vnp_CreateDate = formatter.format(cld.getTime());
				vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
				
				cld.add(Calendar.MINUTE, 15);
				String vnp_ExpireDate = formatter.format(cld.getTime());
				vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
				
				List fieldNames = new ArrayList(vnp_Params.keySet());
				Collections.sort(fieldNames);
				StringBuilder hashData = new StringBuilder();
				StringBuilder query = new StringBuilder();
				Iterator itr = fieldNames.iterator();
				while (itr.hasNext()) {
				    String fieldName = (String) itr.next();
				    String fieldValue = (String) vnp_Params.get(fieldName);
				    if ((fieldValue != null) && (fieldValue.length() > 0)) {
				        //Build hash data
				        hashData.append(fieldName);
				        hashData.append('=');
				        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				        //Build query
				        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				        query.append('=');
				        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				        if (itr.hasNext()) {
				            query.append('&');
				            hashData.append('&');
				        }
				    }
				}
				String queryUrl = query.toString();
				String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
				queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
				String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
				System.out.println(vnp_SecureHash);
				
				URI uri = new URI(paymentUrl);

				// Check if the Desktop is supported (available on desktop environments)
				if (Desktop.isDesktopSupported()) {
				    // Get the Desktop instance
				    Desktop desktop = Desktop.getDesktop();

				    // Open the default web browser with the specified URL
				    desktop.browse(uri);
				} else {
				    // If Desktop is not supported, you can try an alternative approach
				    // (e.g., using Runtime.getRuntime().exec() to open a browser)
				    System.out.println("Desktop not supported, opening URL in alternative way.");
				    // Note: This approach may not work on all systems and is less reliable.
				    // For a more robust solution, consider using a library like JavaFX WebView.
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}