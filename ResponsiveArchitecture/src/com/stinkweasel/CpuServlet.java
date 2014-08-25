package com.stinkweasel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/cpu")
public class CpuServlet extends HttpServlet {
	private static final long serialVersionUID = 93776451L;

	private static final List<Thread> cpuHoggers = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Perform the specified operation
		String operation = request.getParameter("op");
		if (operation != null) {
			switch (operation) {
			case "add": {
				// Spin up a new thread with the CpuHog runnable and start it up
				Thread cpuHog = new Thread(new CpuHog());
				cpuHog.start();
				
				// Add the cpu hog thread to cpuHogger list
				cpuHoggers.add(cpuHog);
				
				break;
			}
			case "remove": {
				if (!cpuHoggers.isEmpty()) {
					// Remove a cpu hog thread from the cpuHoggers list and stop it
					Thread cpuHog = cpuHoggers.remove(0);
					cpuHog.stop();
				}
				
				break;
			}
			default:
				// Any other operation is a no-op
				break;
			}
		}
		
		// Display html with the size of the cpu hogger list
		response.getWriter().write("<h1>CPU HOGGING</h1>");
		response.getWriter().write(String.format("<h3>Running cpu hoggers: %s</h3>", cpuHoggers.size()));
	}

	public CpuServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	class CpuHog implements Runnable {
		boolean running = true;
		
		public void run() {
	        while(running) {
	        	// Do a no-op tight loop to consume cpu
	        }
	    }
		
		public void stop() throws InterruptedException {
			running = false;
		}
		
	}
	
}
