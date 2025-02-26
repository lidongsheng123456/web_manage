package com.example.springboot;

import cn.hutool.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class HttpClientTestBean {

    /**
     * 测试通过httpclient发送GET方式的请求
     */
    @Test
    public void testGET() throws Exception {
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建请求对象，指定URL
        HttpGet httpGet = new HttpGet("https://whois.pconline.com.cn/ipJson.jsp");

        // 发送请求，接受响应结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 获取服务端返回的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务端返回的状态码为：" + statusCode);

        // 获取响应体
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        String substring = body.substring(body.indexOf("ip"), body.indexOf(","));
        System.out.println("服务端返回的数据为：" + substring);

        // 关闭资源
        response.close();
        httpClient.close();
    }

    /**
     * 测试通过httpclient发送POST方式的请求
     */
    @Test
    public void testPOST() throws Exception {
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建请求对象，指定URL
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        // 创建JSON对象并设置参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");

        // 将JSON对象转换为字符串实体
        StringEntity entity = new StringEntity(jsonObject.toString());

        // 指定请求编码方式
        entity.setContentEncoding("utf-8");

        // 设置数据格式为JSON
        entity.setContentType("application/json");

        // 设置请求体
        httpPost.setEntity(entity);

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 解析返回结果，获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应码为：" + statusCode);

        // 获取响应体
        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);
        System.out.println("响应数据为：" + body);

        // 关闭资源
        response.close();
        httpClient.close();
    }
}
