package com.http.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpDemo {

	public static void main(String[] args) {
		HttpDemo httpDemo = new HttpDemo();
		httpDemo.requestByGetMethod();
	}

	/**
	 * ͨ��GET��ʽ����http����
	 */
	public void requestByGetMethod() {
		// ����Ĭ�ϵ�httpClientʵ��
		CloseableHttpClient httpClient = getHttpClient();
		try {
			// ��get��������http����
			HttpGet get = new HttpGet("http://www.baidu.com");
			System.out.println("ִ��get����:...." + get.getURI());
			CloseableHttpResponse httpResponse = null;
			// ����get����
			httpResponse = httpClient.execute(get);
			try {
				// responseʵ��
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out.println("��Ӧ״̬��:" + httpResponse.getStatusLine());
					System.out
							.println("-------------------------------------------------");
					System.out.println("��Ӧ����:" + EntityUtils.toString(entity));
					System.out
							.println("-------------------------------------------------");
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * POST��ʽ����http����
	 */
	public void requestByPostMethod() {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost("http://localhost/...."); // �������ϱ�����ĳ������������
			// ���������б�
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("j_username", "admin"));
			list.add(new BasicNameValuePair("j_password", "admin"));
			// url��ʽ����
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,
					"UTF-8");
			post.setEntity(uefEntity);
			System.out.println("POST ����...." + post.getURI());
			// ִ������
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out
							.println("-------------------------------------------------------");
					System.out.println(EntityUtils.toString(uefEntity));
					System.out
							.println("-------------------------------------------------------");
				}
			} finally {
				httpResponse.close();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
     * ����HttpClient 4.3��ͨ��POST����
     *
     * @param url       �ύ��URL
     * @param paramsMap �ύ<������ֵ>Map
     * @return �ύ��Ӧ
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

	private CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	}

}
