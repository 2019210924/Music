package com.example.heartmusic.utils.httphelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class NetUtil {

    //使用静态内部类的单例模式来创建网络请求专用的线程池,线程安全,同时达到了懒加载效果

    private static class Holder {
        private final static NetUtil INSTANCE = new NetUtil();

    }

    public static NetUtil getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * ThreadPoolExecutor的构造方法中的参数含义
     */
    //核心线程数量,同时能够执行的线程数量
    private int corePoolSize;
    //最大线程的数量,能够容纳的最大排队数
    private int maxPoolSize;
    //正在排队的任务的最长的排队时间
    private long keepAliveTime = 30;
    //正在排队的任务的最长排队时间的单位
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    //线程池对象
    private ThreadPoolExecutor executor;

    //将构造方法设为私有,故无法从其他方式创建对象,构成单例
    private NetUtil() {
        //获取当前设备可用的核心处理器核心数*2+1,赋值给核心线程的数量,能使性能达到极致
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //30应该够......
        maxPoolSize = 30;
        //初始化线程池
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                //缓冲队列,存放排队的任务,类似栈结构
                new LinkedBlockingDeque<Runnable>(),
                //创建线程的工厂(工厂模式?
                Executors.defaultThreadFactory(),
                //处理那些超出最大线程数量的策略
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public void execute(Request request, final CallBack callback) {
        //初始化网络请求参数
        final String getUrl = request.getUrl();
        final String method = request.getMethod();
        final String key[] = request.getKey();
        final String value[] = request.getValue();
        final String contentType = request.getContentType();


        //开始网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String realUrl;
                String data = "";
                if(method.equals("GET")) {
                    realUrl = getUrl + "?" + 1;
                }else {
                    realUrl = getUrl;
                }
                    try {
                        URL url = new URL(realUrl);
                        //此处的urlConnection对象是根据URL的请求协议生成的URLConnection类的子类HttpURLConnection
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        //根据请求的需要设置参数
                        conn.setRequestMethod(method); //默认为GET 所以GET不设置也行
                        conn.setUseCaches(false);
                        conn.setConnectTimeout(5000); //请求超时时间
                        conn.setReadTimeout(5000); //请求读取时间

                        //设置通用的请求属性（还有别的）
                        conn.setRequestProperty("Content-Type", contentType);
                        conn.setRequestProperty("connection", "Keep-Alive");

                        if (method.equals("POST")){
                            //设置请求正文
                            PrintWriter pw=new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                            pw.print(data);
                            pw.flush();
                            pw.close();
                        }
                        //使用 connect 方法建立到远程对象的实际连接。
                        conn.connect();

                        //远程对象变为可用。远程对象的头字段和内容变为可访问。
                        //获取状态码
                        if (conn.getResponseCode() == 200) {
                            //获取头字段
                            Map<String, List<String>> headers = conn.getHeaderFields();
                            System.out.println(headers); //输出头字段

                            //获取正文
                            BufferedReader reader = null;
                            StringBuffer resultBuffer = new StringBuffer();
                            String tempLine = null;

                            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((tempLine = reader.readLine()) != null) {
                                resultBuffer.append(tempLine);
                            }
                            System.out.println(resultBuffer);
                            reader.close();
                            callback.onResponse(resultBuffer.toString());
                        }

                    } catch (MalformedURLException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                        callback.onFailed(e);
                    } catch (IOException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                        callback.onFailed(e);
                    }
            }
        });
    }
}
