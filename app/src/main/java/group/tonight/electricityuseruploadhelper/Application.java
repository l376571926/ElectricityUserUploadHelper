package group.tonight.electricityuseruploadhelper;


import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lzy.okgo.OkGo;

import okhttp3.OkHttpClient;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        OkGo.getInstance()
                .setOkHttpClient(okHttpClient)
                .init(this);
    }
}
