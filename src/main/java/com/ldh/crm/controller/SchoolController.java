package com.ldh.crm.controller;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.ldh.crm.pojo.R;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/school")
@RestController
public class SchoolController {


    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APICODE = "8612409c276f422b80792a9b2becb1af";

    //1.API方法
    public static String getRequest(String path) {
        String result = null;
        String url = "https://api.yonyoucloud.com/apis/dst/collegeInfoQuery/collegeInfoQuery";//请求接口地址
        String method = "GET";
        String paramFormat = "form";
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("name", path);

        Map<String, Object> headerParams = new HashMap<String, Object>();//请求头参数
        headerParams.put("apicode", APICODE);//APICODE

        try {
            result = net(url, params, headerParams, method, paramFormat);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map<String, Object> params, Map<String, Object> headerParams, String method, String paramFormat) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            String contentType = null;
            if (headerParams.containsKey("Content-Type"))
                contentType = headerParams.get("Content-Type").toString();

            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            for (String i : headerParams.keySet()) {
                conn.setRequestProperty(i, headerParams.get(i).toString());
            }
            if ("form".equals(paramFormat) && !"application/x-www-form-urlencoded".equals(contentType) && !"application/xml".equals(contentType)) {
                conn.setRequestProperty("Content-Type", "application/json");
            }
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    if ("form".equals(paramFormat)) {
                        if ("application/x-www-form-urlencoded".equals(contentType))
                            out.writeChars(urlencode(params));
                        else if ("application/xml".equals(contentType))
                            out.writeChars(xmlencode(params));
                        else
                            out.writeChars(jsonencode(params));
                    } else
                        out.writeChars(params.get("textData").toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                if (("").equals(i.getKey())) {
                    sb.append(URLEncoder.encode(i.getValue() + "", "UTF-8"));
                } else {
                    sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    //将map型转为请求参数型
    public static String jsonencode(Map<String, Object> data) {
        JSONObject jparam = new JSONObject();
        for (Map.Entry i : data.entrySet())
            jparam.put(i.getKey(), i.getValue());

        return jparam.toString();
    }

    //将map型转为请求参数型
    public static String xmlencode(Map<String, Object> data) {
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        for (Map.Entry i : data.entrySet())
            xmlData.append("<" + i.getKey() + ">" + i.getValue() + "</" + i.getKey() + ">");

        return xmlData.toString();
    }

    @GetMapping("/getCreated/{path}")
    public String getCreated(@PathVariable String path) {
        return getRequest(path);
    }

    @GetMapping("/getSchool/{path}")
    public String getSchool(@PathVariable String path) {
        String request = getRequest(path);
        return request;
    }
}

