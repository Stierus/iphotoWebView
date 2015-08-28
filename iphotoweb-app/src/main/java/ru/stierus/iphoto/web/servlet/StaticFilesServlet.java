package ru.stierus.iphoto.web.servlet;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pavel on 15.07.15.
 */
public class StaticFilesServlet  extends HttpServlet {

    protected static String JS_TYPE = "js";
    protected static String CSS_TYPE = "css";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try{

        }
        catch (Exception e) {

        }
        response.setContentType("text/html"); // application/x-javascript  text/css
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }

    private String getFileType(String fileName) throws Exception {
        Pattern JsPattern = Pattern.compile("^.*/([-_\\w]+.js)(\\?.*)?$");
        Matcher JsMatcher = JsPattern.matcher(fileName);
        if (JsMatcher.find( )) {
//            return JsMatcher.group(0);
            return JS_TYPE;
        }

        Pattern CssPattern = Pattern.compile("^.*(\\.css)(\\?.*)?$");
        Matcher CssMatcher = JsPattern.matcher(fileName);
        if (CssMatcher.find( )) {
            return CSS_TYPE;
        }

        throw new Exception("unknown file type in request");
    }

    private HttpServletResponse returnCss(HttpServletRequest request, HttpServletResponse response) {

        String pathInfo = request.getPathInfo();

        response.setContentType("text/css");
        response.setStatus(HttpServletResponse.SC_OK);

        return response;
    }
}
