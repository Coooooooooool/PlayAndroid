package com.alex.playandroid.net;




import com.alex.playandroid.module.home.model.HotKeyBean;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.home.model.BannerBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.module.mine.model.CoinRankBean;
import com.alex.playandroid.module.mine.model.CoinRecordBean;
import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.navigate.entity.NaviBean;

import java.util.List;

import io.reactivex.Observer;

public class DataManager {

    private ApiService apiService;

   private DataManager(){
       initDataManager();
   }

    private void initDataManager() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);

    }

    private static class DataManagerInstance{
        private static DataManager dataManager = new DataManager();
    }

    public static DataManager getInstance(){
       return DataManagerInstance.dataManager;
    }


    public void login(String account, String password, Observer<Response<UserBean>> observer){
       apiService.login(account,password)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void register(String account,String password,String repassword,Observer<Response<UserBean>> observer){
       apiService.register(account,password,repassword)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    public void logout(Observer<Response<String>> observer){
       apiService.logout().compose(TransformUtils.applySchedulers()).subscribe(observer);
    }


    public void requestHomeArticleList(int page,Observer<Response<ArticleListBean>> ob){
       apiService.requestArticleList(page)
               .compose(TransformUtils.<Response<ArticleListBean>>applySchedulers())
               .subscribe(ob);
    }


    public void requestBanner(Observer<Response<List<BannerBean>>> observer){
         apiService.requestBanner()
                .compose(TransformUtils.<Response<List<BannerBean>>>applySchedulers())
                .subscribe(observer);
    }

    public void requestKnowledgeList(Observer<Response<List<ChapterBean>>> observer){
       apiService.requestKnowledgeList()
               .compose(TransformUtils.<Response<List<ChapterBean>>>applySchedulers())
               .subscribe(observer);
    }

    public void requestNavigateList(Observer<Response<List<NaviBean>>> observer){
       apiService.requestNavigateList()
               .compose(TransformUtils.<Response<List<NaviBean>>>applySchedulers())
               .subscribe(observer);
    }

    public void requestKnowledgeArticleList(int page,int id,Observer<Response<ArticleListBean>> observer){
       apiService.requestKnowledgeArticleList(page,id)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestTopArticleList(Observer<Response<List<ArticleBean>>> observer){
       apiService.requestTopArticleList()
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    public void requestProjectChapters(Observer<Response<List<ChapterBean>>> observer){
       apiService.requestProjectChapters()
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    public void requestProjectArticleList(int page,int id, Observer<Response<ArticleListBean>> observer){
       apiService.requestProjectArticleList(page,id)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    public void requestHotKeyList(Observer<Response<List<HotKeyBean>>> observer){
       apiService.requestHotKeyList()
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void search(int page, String key, Observer<Response<ArticleListBean>> observer){
       apiService.search(page,key)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    public void collect(int id, Observer<Response<String>> observer){
       apiService.collect(id)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void uncollect(int id, Observer<Response<String>> observer){
       apiService.uncollect(id)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestCollectArticleList(int page,Observer<Response<ArticleListBean>> observer){
       apiService.requestCollectArticleList(page)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestCollectLinkList(Observer<Response<List<CollectionLinkBean>>> observer){
       apiService.requestCollectLinkList()
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestCoin(Observer<Response<Integer>> observer){
       apiService.requestCoin()
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestCoinRecordList(int page, Observer<Response<CoinRecordBean>> observer){
       apiService.requestCoinRecordList(page)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }

    public void requestCoinRankList(int page, Observer<Response<CoinRankBean>> observer){
       apiService.requestCoinRankList(page)
               .compose(TransformUtils.applySchedulers())
               .subscribe(observer);
    }


    //               .subscribe(new Consumer<Response<List<BannerBean>>>() {
//                   @Override
//                   public void accept(Response<List<BannerBean>> listResponse) throws Exception {
//
//                   }
//               });

//               .subscribe(new Observer<Response<List<BannerBean>>>() {
//                   @Override
//                   public void onSubscribe(Disposable d) {
//
//                   }
//
//                   @Override
//                   public void onNext(Response<List<BannerBean>> listResponse) {
//
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//
//                   }
//
//                   @Override
//                   public void onComplete() {
//
//                   }
//               });

//
//    /**
//     * .供电公司数据请求API
//     * @return
//     */
//    public Observable<Company> requestCompanyList(){
//       return customerApi.requestCompanyList();
//    }
//
//
//    /**
//     * 营业厅数据请求API
//     * @param company
//     * @return
//     */
//    public Observable<Hall> requestHallList(String company){
//        return customerApi.requestHallList(company);
//    }
//
//
//    /**
//     * 设备类型数据请求API
//     * @param company
//     * @param hall
//     * @return
//     */
//    public Observable<DeviceType> requestDeviceTypeList(String company, String hall){
//        return customerApi.requestDeviceTypeList(company,hall);
//    }
//
//
//    /**
//     * 请求设备编号API
//     * @param deviceType
//     * @return
//     */
//    public Observable<DeviceCode> requestDeviceCodeList(String company, String hall, String deviceType){
//        return customerApi.requestDeviceCodeList(company,hall,deviceType);
//    }
//
//
//    /**
//     * 绑定设备API
//     * @param company
//     * @param hall
//     * @param deviceType
//     * @param deviceCode
//     * @param jpushId
//     * @return
//     */
//    public Observable<Device> bindingDevice(String company, String hall, String deviceType, String deviceCode, String jpushId){
//        WeakHashMap<String,String> map = new WeakHashMap<>();
//        map.put("company",company);
//        map.put("hall",hall);
//        map.put("device_type",deviceType);
//        map.put("device_code",deviceCode);
//        map.put("jpush_id",jpushId);
//        return customerApi.bindingDevice(map);
//    }
//

//    /**
//     * 增减记录API
//     * @param customerId
//     * @param jpushId
//     * @param option
//     * @return
//     */
//    public Observable<UpdateRecord> updateRecord(String customerId,String jpushId,String accessId,int option,int serviceType,String operatingTime){
//        WeakHashMap<String,String> map = new WeakHashMap<>();
//        map.put("id","");
//        map.put("customer_service",customerId);
//        map.put("jpush_id",jpushId);
//        map.put("status",String.valueOf(option));
//        map.put("accessid",accessId);
//        map.put("service_type",String.valueOf(serviceType));
//        map.put("operating_time",operatingTime);
//        return customerApi.updateRecord(map);
//    }

//    /**
//     * 增减记录API
//     * @param customerId
//     * @param jpushId
//     * @param option
//     * @return
//     */
//    public Observable<UpdateRecord> updateRecord(int id, String customerId, String jpushId, String accessId, int option, int serviceType, String operatingTime){
//        WeakHashMap<String,Object> map = new WeakHashMap<>();
//        map.put("id",id);
//        map.put("customer_service",customerId);
//        map.put("jpush_id",jpushId);
//        map.put("status",String.valueOf(option));
//        map.put("accessid",accessId);
//        map.put("service_type",String.valueOf(serviceType));
//        map.put("operating_time",operatingTime);
//        return customerApi.updateRecord(map);
//    }
//
//
//    /**
//     * 每个客服通话人数API
//     * @return
//     */
////    public Observable<WaitingPeople> requestWaitingPeople(String customerId){
////        return customerApi.requestWaitingPeople(customerId);
////    }
//
//
//    /**
//     * 2.当前排队位置API
//     * @param jpushId
//     * @param accessId
//     * @return
//     */
//    public Observable<WaitingPeople> requestWaitingPeoples(String jpushId, String accessId){
//        return customerApi.requestWaitingPeople(jpushId,accessId);
//    }

}
