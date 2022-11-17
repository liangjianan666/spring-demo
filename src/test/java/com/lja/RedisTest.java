package com.lja;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lja.service.HelloService;
import org.apache.http.Header;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiangJianAn
 * @Description Redis测试类
 * @Date 2022/8/10 14:17
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HelloService helloService;

    //主机端口
    private final static String URL = "http://192.168.80.5";

    //api请求前缀
    private final static String PREFIX = "/api/v4";

    /**
     * 登录成功后，携带token访问其他接口
     */
    @Test
    public void gitLoginSessionSuccess() throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().build();
            //api测试
            HttpGet httpGetUser = new HttpGet(URL + PREFIX + "/users");
            httpGetUser.setHeader("Cookie", "_gitlab_session=" + "4856ea1a920ac6993501fb1e367712cf");
            CloseableHttpResponse httpGetUserResponse = httpClient.execute(httpGetUser);
            String responseStr1 = null;
            responseStr1 = getResponseStr(httpGetUserResponse);
            JSONArray objects = JSONObject.parseArray(responseStr1);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 登录测试1
     *
     * @throws IOException
     */
    @Test
    public void gitLoginSession() throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().build();
            String username = "liangjianan";
            String password = "frank18201728912";
            // 爬取页面内容
            HttpGet getCsrfToken = new HttpGet(URL);
            CloseableHttpResponse getCsrfTokenResponse = httpClient.execute(getCsrfToken);
            // 匹配   authenticity_token
            Matcher csrfTokenMatcher = Pattern.compile("(?<=<meta name=\"csrf-token\" content=\").*(?=\")").matcher(EntityUtils.toString(getCsrfTokenResponse.getEntity(), "UTF-8"));
            String csrfToken = null;
            if (csrfTokenMatcher.find()) {
                csrfToken = csrfTokenMatcher.group();
            }

            // 设置body
            List<NameValuePair> paramList = new ArrayList<>();
            paramList.add(new BasicNameValuePair("utf8", "✓"));
            paramList.add(new BasicNameValuePair("authenticity_token", csrfToken));
            paramList.add(new BasicNameValuePair("user[login]", username));
            paramList.add(new BasicNameValuePair("user[password]", password));
            paramList.add(new BasicNameValuePair("user[remember_me]", "0"));

            HttpPost getSession = new HttpPost(URL + "/users/sign_in");
            getSession.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
            Header[] headersCookie = getCsrfTokenResponse.getHeaders("Set-Cookie");
            for (Header header : headersCookie) {
                Matcher logoutSessionMatcher = Pattern.compile("(?<=_gitlab_session=).+?(?=;)").matcher(header.getValue());
                if (logoutSessionMatcher.find()) {
                    // 设置Header
                    getSession.setHeader("Cookie", "_gitlab_session=" + logoutSessionMatcher.group());
                    break;
                }
            }
            // 获取登录后的session
            CloseableHttpResponse getSessionResponse = httpClient.execute(getSession);
            Header[] headersCookieRsponse = getSessionResponse.getHeaders("Set-Cookie");
            // 匹配登录后的_gitlab_session
            String loginSession = null;
            for (Header header : headersCookieRsponse) {
                Matcher loginSessionMatcher = Pattern.compile("(?<=_gitlab_session=).+?(?=;)").matcher(header.getValue());
                if (loginSessionMatcher.find()) {
                    loginSession = loginSessionMatcher.group();
                    break;
                }
            }
            System.out.println("登录成功后，session: " + loginSession);
            //拿到git_session去请求 api接口  登陆成功页面 可实现
            CloseableHttpResponse response = httpClient.execute(getCsrfToken);
            String responseStr = getResponseStr(response);

            //api测试
//            HttpGet httpGetUser = new HttpGet(URL + PREFIX + "/users");
//            CloseableHttpResponse httpGetUserResponse = httpClient.execute(httpGetUser);
//            String responseStr1 = getResponseStr(httpGetUserResponse);
//            JSONArray objects = JSONObject.parseArray(responseStr1);

            System.out.println("====================结束符========================================");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //返回字符串
    private String getResponseStr(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String jsonResponse = EntityUtils.toString(entity, "UTF-8");
        System.out.println(jsonResponse);
        return jsonResponse;
    }

    @Test
    public void transactionTest() {
        Random random = new Random();
        int i = random.nextInt(10000);
        helloService.hello(i);
    }

    @Test
    public void test1() {
        redisTemplate.opsForValue().set("name1", "lja", 3000, TimeUnit.SECONDS);
        String name1 = redisTemplate.opsForValue().get("name1");
        System.out.println(name1);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[] a = new int[10];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        System.arraycopy(a, 2, a, 3, 3);
//        a[2]=99;
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

//        int[] arr = {4, 3, 7, 5, 10, 9, 1, 6, 8, 2};
        //快速排序
//        sort(arr, 0, arr.length - 1);
//        sort1(arr);
//        List<String> list = new ArrayList<>();
//        List<Integer> list1 = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        Class<? extends List> aClass = list.getClass();
//        Class<? extends List> aClass1 = list1.getClass();
//        if(aClass == aClass1) {
//            System.out.println("true");
//        }
//        Iterator<String> iterator = list.iterator();
//        if (iterator.hasNext()) {
//            String next = iterator.next();
//            if("1".equals(next)) {
//                iterator.remove();
//            }
//        }

        //.next() 必须在 .remove() 之前调用。在 foreach 循环中，
        // 编译器会在删除元素的操作后调用 .next()，导致ConcurrentModificationException

//        for (String s : list) {
//            /*if("3".equals(s)) {
//                list.remove(s);
//            }*/
//            //不要在for循环、foreach集合里面删除元素，会有ConcurrentModificationException
//        }
//        System.out.println(list);


//        CountDownLatch count = new CountDownLatch(10);
    }

    /**
     * 快速排序（挖坑法递归）
     *
     * @param arr  待排序数组
     * @param low  左边界
     * @param high 右边界
     */
    public static void sort(int arr[], int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;
        int temp = arr[left]; //挖坑1：保存基准的值

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right]; //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left]; //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
        }
        arr[left] = temp; //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        sort(arr, low, left - 1);
        sort(arr, left + 1, high);
    }

    /**
     * 直接插入排序
     *
     * @param a
     */
    public static void sort1(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }

        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            int temp = a[i]; // 先取出待插入数据保存，因为向后移位过程中会把覆盖掉待插入数
            while (j >= 0 && a[j] > temp) { // 如果待是比待插入数据大，就后移
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = temp; // 找到比待插入数据小的位置，将待插入数据插入
        }
    }
}
