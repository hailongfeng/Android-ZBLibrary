/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zblibrary.demo.application;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.interceptor.HttpLoggingInterceptor;

import zblibrary.demo.manager.DataManager;
import zblibrary.demo.model.User;
import zuo.biao.library.base.BaseApplication;
import zuo.biao.library.util.StringUtil;

/**Application
 * @author Lemon
 */
public class DemoApplication extends BaseApplication {
	private static final String TAG = "DemoApplication";

	private static DemoApplication context;
	public static DemoApplication getInstance() {
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		initHttp();
		Logger.addLogAdapter(new AndroidLogAdapter());
	}

	void initHttp(){
		EasyHttp.init(context);//默认初始化,必须调用

		//全局设置请求头
//		HttpHeaders headers = new HttpHeaders();
//		headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
		//全局设置请求参数
//		HttpParams params = new HttpParams();
//		params.put("appId", AppConstant.APPID);
		String Url=null;
		//以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
		EasyHttp.getInstance()

				//可以全局统一设置全局URL
//				.setBaseUrl(Url)//设置全局URL  url只能是域名 或者域名+端口号

				// 打开该调试开关并设置TAG,不需要就不要加入该行
				// 最后的true表示是否打印内部异常，一般打开方便调试错误
				.debug("EasyHttp", true)

				//如果使用默认的60秒,以下三行也不需要设置
				.setReadTimeOut(60 * 1000)
				.setWriteTimeOut(60 * 100)
				.setConnectTimeout(60 * 100)

				//可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
				//不需要可以设置为0
				.setRetryCount(3)//网络不好自动重试3次
				//可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
				.setRetryDelay(500)//每次延时500ms重试
				//可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
				.setRetryIncreaseDelay(500)//每次延时叠加500ms

				//可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
				.setCacheMode(CacheMode.NO_CACHE)
				//可以全局统一设置缓存时间,默认永不过期
				.setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
				//全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
				.setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
				//全局设置自定义缓存大小，默认50M
				.setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
				//设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
				.setCacheVersion(1)//缓存版本为1
				//.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用
				//可以设置https的证书,以下几种方案根据需要自己设置
				.setCertificates()                                  //方法一：信任所有证书,不安全有风险
				//.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
				//配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
				//.setHostnameVerifier(new SafeHostnameVerifier())
				//.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
//				.addCommonHeaders(headers)//设置全局公共头
//				.addCommonParams(params)//设置全局公共参数
				//.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
				//.setCallFactory()//局设置Retrofit对象Factory
				//.setCookieStore()//设置cookie
				//.setOkproxy()//设置全局代理
				//.setOkconnectionPool()//设置请求连接池
				//.setCallbackExecutor()//全局设置Retrofit callbackExecutor
				//可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
				//.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
				.addInterceptor(new HttpLoggingInterceptor("API"));//添加参数签名拦截器
	}

	
	/**获取当前用户id
	 * @return
	 */
	public long getCurrentUserId() {
		currentUser = getCurrentUser();
		Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
		return currentUser == null ? 0 : currentUser.getId();
	}
	/**获取当前用户phone
	 * @return
	 */
	public String getCurrentUserPhone() {
		currentUser = getCurrentUser();
		return currentUser == null ? null : currentUser.getPhone();
	}


	private static User currentUser = null;
	public User getCurrentUser() {
		if (currentUser == null) {
			currentUser = DataManager.getInstance().getCurrentUser();
		}
		return currentUser;
	}

	public void saveCurrentUser(User user) {
		if (user == null) {
			Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
			return;
		}
		if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
			Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
					" && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
			return;
		}

		currentUser = user;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}

	public void logout() {
		currentUser = null;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}
	
	/**判断是否为当前用户
	 * @param userId
	 * @return
	 */
	public boolean isCurrentUser(long userId) {
		return DataManager.getInstance().isCurrentUser(userId);
	}

	public boolean isLoggedIn() {
		return getCurrentUserId() > 0;
	}



}
