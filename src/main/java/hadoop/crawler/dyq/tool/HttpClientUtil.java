/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-27 13:03
 **/
public class HttpClientUtil {

    // utf-8�ַ�����
    public static final String CHARSET_UTF_8 = "utf-8";

    // HTTP�������͡�
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP�������͡��൱��form������ʽ���ύ����
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP�������͡��൱��form������ʽ���ύ����
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";


    // ���ӹ�����
    private static PoolingHttpClientConnectionManager pool;

    // ��������
    private static RequestConfig requestConfig;

    static {

        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build());
            //����ͬʱ֧�� HTTP �� HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                "https", sslsf).build();
            // ��ʼ�����ӹ�����
            pool = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
            // ��������������ӵ�200��ʵ����Ŀ��ô������ļ��ж�ȡ���ֵ
            pool.setMaxTotal(200);
            // �������·��
            pool.setDefaultMaxPerRoute(2);
            // ����Ĭ�ϳ�ʱ���Ƴ�ʼ��requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                connectionRequestTimeout).setConnectTimeout(
                connectTimeout).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpClient = HttpClients.custom()
            // �������ӳع���
            .setConnectionManager(pool)
            // ������������
            .setDefaultRequestConfig(requestConfig)
            // �������Դ���
            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
            .build();

        return httpClient;
    }

    /**
     * ����Get����
     *
     * @param httpGet
     * @return
     */
    private static byte[] sendHttpGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        // ��Ӧ����
        byte[] responseContent = null;
        try {
            // ����Ĭ�ϵ�httpClientʵ��.
            httpClient = getHttpClient();
            // ����������Ϣ
            httpGet.setConfig(requestConfig);
            // ִ������
            response = httpClient.execute(httpGet);
            // �õ���Ӧʵ��
            entity = response.getEntity();
            // �ж���Ӧ״̬
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                    "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return responseContent;
    }


    /**
     * ���� get����
     *
     * @param httpUrl
     */
    public static byte[] sendHttpGet(String httpUrl) {
        // ����get����
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }



    /**
     * ��map���ϵļ�ֵ��ת���ɣ�key1=value1&key2=value2 ����ʽ
     *
     * @param parameterMap
     *            ��Ҫת���ļ�ֵ�Լ���
     * @return �ַ���
     */
    public static String convertString2Paramter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    public static void main(String[] args) throws Exception {

        byte[] s = sendHttpGet("https://static.pexels.com/photos/954559/pexels-photo-954559.jpeg?cs=srgb&dl=attractive-beautiful-beauty-954559.jpg&fm=jpg");
        BufferedOutputStream bos = new BufferedOutputStream(
            new FileOutputStream("E:\\CODE\\javac\\HadoopSpyder\\src\\main\\resources\\test.jpg")
        );
        bos.write(s);

    }
}
