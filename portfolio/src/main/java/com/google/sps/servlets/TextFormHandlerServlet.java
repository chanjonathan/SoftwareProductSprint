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
 * Handles requests sent to the /text-form-handler URL. Takes given list and tries to sort and return it.
 */


@WebServlet("/text-form-handler")
public class TextFormHandlerServlet extends HttpServlet {

    ArrayList<String> list;
    boolean ready;

    public TextFormHandlerServlet() {
        list = new ArrayList<String>();
        list.add("No input has been recieved");

        ready = false;
    }

    public void selectionSort(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            Integer smallest = i;
    
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(smallest)) {
                    smallest = j;
                }
            }
            
            Integer temp = list.get(i);
            list.set(i, list.get(smallest));
            list.set(smallest, temp);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String string;

        if (ready) {
            string = gson.toJson(list);
        } else {
            string = gson.toJson(new ArrayList<Integer>());
        }
        response.setContentType("text/html;");
        response.getWriter().println(string);
        
        ready = false;
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
            list = new ArrayList<String>();
            list.add("Invalid character in submission.");

            response.sendRedirect("https://jchan-sps-summer22.appspot.com#form");
            ready = true;
            return;
        }

        selectionSort(intList);

        list = new ArrayList<String>();
        for (Integer number : intList) {
            list.add(String.valueOf(number));
        }

        response.sendRedirect("https://jchan-sps-summer22.appspot.com#form");
        ready = true;
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}