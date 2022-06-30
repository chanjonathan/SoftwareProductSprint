// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Takes an image submitted by the user and uploads it to Cloud Storage, and then displays it as
 * HTML in the response.
 */
@WebServlet("/file-upload")
@MultipartConfig
public class FileFormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Get the message entered by the user.
    String fileSuffix = request.getParameter("file-suffix");
    String jobKeywords = request.getParameter("job-keywords");
    String additionaInput = request.getParameter("additional-input");


    // Get the file chosen by the user.
    Part filePart = request.getPart("file");
    String fileName = filePart.getSubmittedFileName();
    InputStream fileInputStream = filePart.getInputStream();

    // Upload the file and get its URL
    String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);


    // Run Python script
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec("ls");

    ProcessBuilder builder = new ProcessBuilder("pwd");
    builder.redirectErrorStream(true);
    Process process = builder.start();
    InputStream is = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    
    // Output some HTML that shows the data the user entered.
    // You could also store the uploadedFileUrl in Datastore instead.
    PrintWriter out = response.getWriter();
    
    String line = null;
    while ((line = reader.readLine()) != null) {
       out.println(line);
    }
  }

  /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(String fileName, InputStream fileInputStream) {
    String projectId = "jchan-sps-summer22";
    String bucketName = "jchan-sps-summer22.appspot.com";
    Storage storage =
        StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    BlobId blobId = BlobId.of(bucketName, fileName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    // Upload the file to Cloud Storage.
    Blob blob = storage.create(blobInfo, fileInputStream);

    // Return the uploaded file's URL.
    return blob.getMediaLink();
  }
}