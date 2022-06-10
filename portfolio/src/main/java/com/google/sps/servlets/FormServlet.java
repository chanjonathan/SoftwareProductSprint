package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.Collections;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Handles requests sent to the /hello URL. Try running a server and navigating
 * to /hello!
 */
@WebServlet("/form-handler")
public class FormServlet extends HttpServlet {

    ArrayList<String> list;

    public FormServlet() {
        list = new ArrayList<String>();
        list.add("No input has been recieved");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        String string = gson.toJson(list);

        response.setContentType("text/html;");
        response.getWriter().println(string);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String text = getParameter(request, "text-input", "");
        String[] array = text.split(",");

        ArrayList<String> stringList = new ArrayList<String>();
        for (String item : array) {
            stringList.add(item.trim());
        }

        ArrayList<Integer> intList = new ArrayList<Integer>();
        try {
            for (String string : stringList) {
                intList.add(Integer.parseInt(string));
            }
        } catch (Exception exception) {
            // response.setContentType("text/html;");
            // response.getWriter().println("Invalid character in submission.");

            list = new ArrayList<String>();
            list.add("Invalid character in submission.");
            
            response.sendRedirect("https://jchan-sps-summer22.appspot.com#form");

            return;
        }

        Collections.sort(intList);

        // response.setContentType("text/html;");
        // response.getWriter().println(intList.toString());

        list = new ArrayList<String>();
        for (Integer number : intList) {
            list.add(String.valueOf(number));
        }

        response.sendRedirect("https://jchan-sps-summer22.appspot.com#form");
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
