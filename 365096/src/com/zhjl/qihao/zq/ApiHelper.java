package com.zhjl.qihao.zq;

import android.content.Context;
import android.util.Log;

import com.zhjl.qihao.Session;
import com.zhjl.qihao.util.SSLSocketClient;

import java.io.IOException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.ConscryptPlatform;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;


/**
 * Retrofit 使用工具类 单例
 */
public class ApiHelper {

    private static final String TAG = "==ApiHelper";
    private Context mContext;
    private static ApiHelper mInstance;
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private ApiHelper() {
        this(30, 30, 30);
    }

    public ApiHelper(int connTimeout, int readTimeout, int writeTimeout) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
//                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .addInterceptor(mTokenInterceptor);

        mHttpClient = builder.build();
    }

    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder authorised = originalRequest.newBuilder();
            String url = originalRequest.url().toString();
            if (!Session.get(mContext).isTest()) {
                // 如果是测试服并且拦截下来的url中还包含正式服，将url前缀替换成测试服的url前缀
                if (url.contains("tj.")) {
                    url = url.replace("tj.", "psms.");
                } else if (url.contains("tp.")) {
                    url = url.replace("tp.", "mall.");
                }
            } else {
                if (url.contains("psms.")) {
                    url = url.replace("psms.", "tj.");
                } else if (url.contains("mall.")) {
                    url = url.replace("mall.", "tp.");
                }
            }
            // 将新的 url 再设置回去
            if (Session.get(mContext).getmToken() == null) {
                originalRequest = authorised.url(url).build();
                return chain.proceed(originalRequest);
            }
            originalRequest = authorised.url(url).header("token", Session.get(mContext).getmToken()).build();
            return chain.proceed(originalRequest);
        }
    };

    public static ApiHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ApiHelper();
        }
        return mInstance;
    }

    public ApiHelper buildRetrofit(Context mContext) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ZqConstant.BASE_URL_JAVA_ONLINE)
                .client(mHttpClient)
                .build();
        this.mContext = mContext;
        return this;
    }


    public <T> T createService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
