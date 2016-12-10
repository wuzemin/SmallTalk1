package com.min.smalltalk.network;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by Min on 2016/9/10.
 */
public class HttpUtils {
    private static final String LOCAL_RUL ="http://192.168.0.178:8080";
    private static final String BASE_RUL ="http://192.168.0.209:80/appapi/app";
    public static final String IMAGE_RUL ="http://192.168.0.209:80";



    /**
     * 设置cookie
     * @param context
     */
    public static void setCookie(Context context){
        CookieStore cookieStore=new PersistentCookieStore(context);
        CookieJarImpl cookieJar=new CookieJarImpl(cookieStore);
        OkHttpClient client=new OkHttpClient.Builder().cookieJar(cookieJar).build();
        OkHttpUtils.initClient(client);
    }

    //普通的GET请求，根据泛型Bean返回值也是Bean
    public static void getRequest(String url, StringCallback callback) {
        OkHttpUtils.get().url(BASE_RUL +url).build().execute(callback);
    }

    //好友列表
    public static void postRequest(String url, String userid, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url).addParams("userid",userid).build().execute(callback);
    }

    public static void postListRequest(String url, String text, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("text",text)
//                .addParams("list",list)
                .build().execute(callback);
    }

    //登录
    public static void postLoginRequest(String url,String username,String password,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("phone",username)
                .addParams("password",password)
                .build().execute(callback);
    }

    //好友请求列表
    public static void postAddFriendsRequest(String url, String userId,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url).addParams("userid",userId).build().execute(callback);
    }
    //确认好友添加请求
    public static void postEnterFriendRequest(String url,String userid,String friendid, int status, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("userid",userid)
                .addParams("f_userid",friendid)
                .addParams("status", String.valueOf(status)).build().execute(callback);
    }

    //普通Post上传----用户登录
    public static void sendPostRequest(String url,String username,String password,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("username",username)
                .addParams("password",password)
                .build().execute(callback);
    }


    //直接上传String类型的文本
    public static void sendPostStringRequest(String url,Object object,StringCallback callback){
        OkHttpUtils.postString().url(BASE_RUL +url).content(object.toString()).build().execute(callback);
    }


    //搜索好友
    public static void PostSearchFriendRequest(String url, String string, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL + url)
                .addParams("user",string)
                .build().execute(callback);
    }

    public static void senddPostRequest(String url,String userid, String nickname, String f_userid,String message,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("userid",userid)
                .addParams("nickname",nickname)
                .addParams("f_userid",f_userid)
                .addParams("addFriendMessage",message)
                .build()
                .execute(callback);
    }

    //判断该手机是否已近注册
    public static void postPhoneRequest(String url,String phone,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("phone",phone)
                .build().execute(callback);
    }

    //注册
    public static void postRegisterRequest(String url, String nickname, String phone,String password, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("nickname",nickname)
                .addParams("phone",phone)
                .addParams("password",password).build().execute(callback);
    }

    //好友信息
    public static void postFriendsRequest(String url,String userId,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("userId",userId)
                .build().execute(callback);
    }

    //创建群组
    public static void sendPostListRequest(String url,String userid, String text, String aaa, File file, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("groupName",text)
                .addParams("userId",userid)
                .addParams("groupUser", aaa)
                .addFile("file","crop_file.jpg",file)
                .build()
                .execute(callback);
    }

    public static void sendPostTestRequest(String url, String groupName, File file, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("groupName",groupName)
                .addFile("file","crop_file.jpg",file)
                .build()
                .execute(callback);
    }


    //群组列表
    public static void postGroupListRequest(String url, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url).build().execute(callback);
    }
    //群组信息---群组成员
    public static void postGroupsRequest(String url,String groupId,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId).build().execute(callback);
    }
    //修改群头像
    public static void postChangeGroupHead(String url,String groupId,File file,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId)
                .addFile("file","",file)
                .build().execute(callback);
    }
    //修改群名称
    public static void postChangeGroupName(String url,String groupId,String groupName,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId)
                .addParams("groupName",groupName)
                .build().execute(callback);
    }
    //退出群
    public static void postQuitGroup(String url,String groupId,String userId,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId)
                .addParams("userId",userId)
                .build().execute(callback);
    }
    //解散群
    public static void postDismissGroup(String url,String groupId,String userId,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId)
                .addParams("userId",userId)
                .build().execute(callback);
    }
    //群活动
    public static void postGroupActiivity(String url,String groupId ,StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL+url)
                .addParams("groupId",groupId)
                .build().execute(callback);
    }
    //删除好友
    public static void postDelFriendRequest(String url, String userId, String friendId, StringCallback callback){
        OkHttpUtils.post().url(BASE_RUL +url)
                .addParams("userId",userId).addParams("friendId",friendId).build().execute(callback);
    }

}
