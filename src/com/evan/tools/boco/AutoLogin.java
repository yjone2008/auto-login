package com.evan.tools.boco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class AutoLogin
  implements Serializable
{
  private static Logger logger = Logger.getLogger(AutoLogin.class);
  private static final long serialVersionUID = 7384917098844848336L;
  private static String USERNAME = "zhangfuliang";
  private static String PASSWORD = "s%C2%F1%C0%F5%7C%2F.%B1Ps";
  private static String LOGON_SITE = "http://10.127.10.2";
  private static int LOGON_PORT = 90;
  
  public static void main(String[] args)
    throws Exception
  {
    logger.info("Invoke auto login.");
    AutoLogin al = new AutoLogin();
    if ((args.length != 0) && ("auto".equalsIgnoreCase(args[0])))
    {
      logger.info("begin.");
      logger.info("Login : " + (al.login() ? "Success*****" : "Fault#####"));
      return;
    }
    logger.error("error");
    logger.debug("debug");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String temp = null;
    System.out.println("Username[hanrui]:");
    temp = input.readLine();
    if ((temp != null) && (!temp.equals(""))) {
      USERNAME = temp;
    }
    System.out.println("Encrypt password[*********]:");
    temp = input.readLine();
    if ((temp != null) && (!temp.equals(""))) {
      PASSWORD = temp;
    }
    for (;;)
    {
      System.out.println("Input process type. A:LOGIN, B:LOGOUT, C:EXIT. ");
      temp = input.readLine();
      if (!"".equals(temp)) {
        if ("A".equalsIgnoreCase(temp.trim()))
        {
          System.out.println("Login : " + (al.login() ? "Success." : 
            "Fault."));
        }
        else if ("B".equalsIgnoreCase(temp.trim()))
        {
          System.out.println("Logout : " + (al.logout() ? "Success." : 
            "Fault."));
        }
        else
        {
          if ("C".equalsIgnoreCase(temp.trim()))
          {
            System.out.println("Exit.");
            break;
          }
          System.out.println("Input error, please input again.");
        }
      }
    }
  }
  
  private boolean logout()
    throws Exception
  {
    HttpClient client = new HttpClient();
    client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
    
    PostMethod post = new PostMethod("http://10.127.10.2:90/login");
    NameValuePair ie = new NameValuePair("User-Agent", 
      "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
    NameValuePair loginType = new NameValuePair("login_type", "logout");
    post.setRequestBody(new NameValuePair[] { ie, loginType });
    client.executeMethod(post);
    String responseString = new String(post.getResponseBody(), "UTF-8");
    
    Cookie[] cookies = client.getState().getCookies();
    client.getState().addCookies(cookies);
    post.releaseConnection();
    return responseString.contains("用户正常退出");
  }
  
  private boolean login()
    throws Exception
  {
    HttpClient client = new HttpClient();
    client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
    
    PostMethod post = new PostMethod("http://10.127.10.2:90/login");
    NameValuePair ie = new NameValuePair("User-Agent", 
      "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
    NameValuePair uri = new NameValuePair("uri", "d3d3LmJhaWR1LmNvbS8=");
    NameValuePair terminal = new NameValuePair("terminal", "mobile");
    NameValuePair loginType = new NameValuePair("login_type", "login");
    NameValuePair showChangePassword = new NameValuePair("show_change_password", 
      "block");
    NameValuePair shortMessage = new NameValuePair("short_message", "none");
    NameValuePair showCaptcha = new NameValuePair("show_captcha", "none");
    NameValuePair showAssure = new NameValuePair("show_assure", "none");
    NameValuePair assurePhone = new NameValuePair("assure_phone", "");
    NameValuePair captchaValue = new NameValuePair("captcha_value", "");
    NameValuePair saveUser = new NameValuePair("save_user", "1");
    NameValuePair savePass = new NameValuePair("save_pass", "1");
    NameValuePair username = new NameValuePair("username", USERNAME);
    NameValuePair password = new NameValuePair("password", PASSWORD);
    post.setRequestBody(new NameValuePair[] { ie, 
      uri, 
      terminal, 
      loginType, 
      showChangePassword, 
      shortMessage, 
      showCaptcha, 
      showAssure, 
      assurePhone, 
      captchaValue, 
      saveUser, 
      savePass, 
      username, 
      password });
    client.executeMethod(post);
    String responseString = new String(post.getResponseBody(), "UTF-8");
    
    Cookie[] cookies = client.getState().getCookies();
    client.getState().addCookies(cookies);
    post.releaseConnection();
    return responseString.contains("<p>恭喜！<b>" + username.getValue() + 
      "</b> 已成功登录。</p>");
  }
}
