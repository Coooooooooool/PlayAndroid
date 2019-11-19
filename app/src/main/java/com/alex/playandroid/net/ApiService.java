package com.alex.playandroid.net;

import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.home.model.BannerBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.module.mine.model.CoinRankBean;
import com.alex.playandroid.module.mine.model.CoinRecordBean;
import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.main.model.UsefulWebBean;
import com.alex.playandroid.module.home.model.HotKeyBean;
import com.alex.playandroid.module.navigate.entity.NaviBean;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    /**
     * 登录
     * 方法： POST
     * 参数：
     * username，password
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<Response<UserBean>> login(@Field("username") String username,
                                         @Field("password") String password);

    /**
     * 注册
     * 方法： POST
     * 参数：
     * username，password,repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<Response<UserBean>> register(@Field("username") String username,
                                            @Field("password") String password,
                                            @Field("repassword") String repassword);

    /**
     * 退出
     * 方法： GET
     * 访问了 logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），
     * 如果客户端 Cookie 实现合理，可以实现自动清理，如果本地做了用户账号密码和保存，及时清理。
     */
    @GET("user/logout/json")
    Observable<Response<String>> logout();


//    -================================  首页  ================================-

    /**
     * 请求首页文章列表
     *
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<Response<ArticleListBean>> requestArticleList(@Path("page") int page);


    /**
     * 请求首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<Response<List<BannerBean>>> requestBanner();


    /**
     * 请求首页置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    Observable<Response<List<ArticleBean>>> requestTopArticleList();


    /**
     * 请求常用网站
     *
     * @return
     */
    @GET("friend/json")
    Observable<Response<List<UsefulWebBean>>> requestCommonWebsite();






//    -================================ 搜索 ================================-

    /**
     * 请求搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<Response<List<HotKeyBean>>> requestHotKeyList();


    /**
     * 搜索
     * 方法：POST
     * 参数：
     * 页码：拼接在链接上，从0开始。
     * k ： 搜索关键词
     * 支持多个关键词，用空格隔开
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<Response<ArticleListBean>> search(@Path("page") int page,
                                                    @Field("k") String key);





//    -================================  知识体系  ================================-

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<Response<List<ChapterBean>>> requestKnowledgeList();


    /**
     * 知识体系下的文章
     * 方法：GET
     * 参数：
     * cid 分类的id，上述二级目录的id
     * 页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    Observable<Response<ArticleListBean>> requestKnowledgeArticleList(@Path("page") int page, @Query("cid") int id);




//    -================================ 导航 ================================-

    /**
     * 导航
     *
     * @return
     */
    @GET("navi/json")
    Observable<Response<List<NaviBean>>> requestNavigateList();




//    -================================ 项目 ================================-

    /**
     * 项目分类
     * 方法： GET
     */
    @GET("project/tree/json")
    Observable<Response<List<ChapterBean>>> requestProjectChapters();

    /**
     * 项目列表数据
     * 方法：GET
     * 参数：
     * cid 分类的id，上面项目分类接口
     * 页码：拼接在链接中，从1开始。
     */
    @GET("project/list/{page}/json")
    Observable<Response<ArticleListBean>> requestProjectArticleList(@Path("page") int page,
                                                                @Query("cid") int id);




//    -================================ 收藏 ================================-

    /**
     * 收藏文章列表
     * 方法：GET
     * 参数： 页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{page}/json")
    Observable<Response<ArticleListBean>> requestCollectArticleList(@Path("page") int page);

    /**
     * 收藏网站列表
     * 方法：GET
     */
    @GET("lg/collect/usertools/json")
    Observable<Response<List<CollectionLinkBean>>> requestCollectLinkList();

    /**
     * 收藏站内文章
     * 方法：POST
     * 参数： 文章id，拼接在链接中。
     */
    @POST("lg/collect/{id}/json")
    Observable<Response<String>> collect(@Path("id") int id);

    /**
     * 收藏站外文章
     * 方法：POST
     * 参数：
     * title，author，link
     */
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<Response<ArticleBean>> collect(@Field("title") String title,
                                              @Field("author") String author,
                                              @Field("link") String link);

    /**
     * 收藏网址
     * 方法：POST
     * 参数：
     * name,link
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Observable<Response<CollectionLinkBean>> collect(@Field("name") String name,
                                                     @Field("link") String link);

    /**
     * 取消收藏 文章列表
     * 方法：POST
     * 参数：
     * id:拼接在链接上 id传入的是列表中文章的id。
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<Response<String>> uncollect(@Path("id") int id);

    /**
     * 删除收藏网站
     * 方法：POST
     * 参数：
     * id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Observable<Response<String>> uncollectLink(@Field("id") int id);

    /**
     * 编辑收藏网站
     * 方法：POST
     * 参数：
     * id,name,link
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Observable<Response<CollectionLinkBean>> updateCollectLink(@Field("id") int id,
                                                               @Field("name") String name,
                                                               @Field("link") String link);

    /**
     * 取消收藏 我的收藏页面（该页面包含自己录入的内容）
     * 方法：POST
     * 参数：
     * id:拼接在链接上
     * originId:列表页下发，无则为-1
     * originId 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<Response<String>> uncollect(@Path("id") int id,
                                           @Field("originId") int originId);




//    -================================ 积分 ================================-
    /**
     * 获取个人积分
     */
    @GET("lg/coin/getcount/json")
    Observable<Response<Integer>> requestCoin();

    /**
     * 获取个人积分获取列表
     * page 1开始
     */
    @GET("lg/coin/list/{page}/json")
    Observable<Response<CoinRecordBean>> requestCoinRecordList(@Path("page") int page);

    /**
     * 积分排行榜接口
     * page 1开始
     */
    @GET("coin/rank/{page}/json")
    Observable<Response<CoinRankBean>> requestCoinRankList(@Path("page") int page);

//
//    /**
//     * 2.营业厅数据请求API
//     * @param company 公司
//     * @return 营业厅列表
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_hall_api")
//    Observable<Hall> requestHallList(@Field("company") String company);
//
//
//    /**
//     * 3.设备类型数据请求API
//     * @param company 公司
//     * @param hall 营业厅
//     * @return 机器类型列表
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_devicetype_api")
//    Observable<DeviceType> requestDeviceTypeList(@Field("company") String company, @Field("hall") String hall);
//
//
//    /**
//     * 4.请求设备编号API
//     * @param deviceType 机器类型
//     * @return 设备编号列表
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_machine_code_api")
//    Observable<DeviceCode> requestDeviceCodeList(@Field("company") String company, @Field("hall") String hall, @Field("device_type") String deviceType);
//
//
//    /**
//     * 5.绑定设备API
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_add_jupsh_api ")
//    Observable<Device> bindingDevice(@FieldMap WeakHashMap<String, String> params);
//
//
//    /**
//     * 1.增减记录API
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_call_record_api")
//    Observable<UpdateRecord> updateRecord(@FieldMap WeakHashMap<String, Object> params);
//
//
//
//
//    @FormUrlEncoded
//    @POST("jupsh_android_call_record_num_api")
//    Observable<WaitingPeople> requestWaitingPeople(@Field("customer_service") String customerId);
//
//
//    /**
//     * 当前排队位置API
//     * @param jpushId 设备极光id
//     * @param accessId 客服账户id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("jupsh_android_call_queue_num_api")
//    Observable<WaitingPeople> requestWaitingPeople(@Field("jpush_id") String jpushId, @Field("accessid") String accessId);

}
