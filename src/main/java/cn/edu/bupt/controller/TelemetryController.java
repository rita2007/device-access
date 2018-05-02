package cn.edu.bupt.controller;


import cn.edu.bupt.exception.DeviceAccessException;
import cn.edu.bupt.pojo.kv.TsKvEntry;
import cn.edu.bupt.pojo.kv.TsKvQuery;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/data")
public class TelemetryController extends BaseController {

    @RequestMapping(value="alldata/{deviceId}/{queries}",method = RequestMethod.GET)
    public ListenableFuture<List<TsKvEntry>> getAllData(@PathVariable("deviceId") String deviceId,
                                                        @PathVariable("queries") List<TsKvQuery> queries) throws DeviceAccessException {
        try{
            ListenableFuture<List<TsKvEntry>> listListenableFuture = baseTimeseriesService.findAll(toUUID(deviceId),queries);
            return listListenableFuture;
        }catch (Exception e){
            throw handleException(e);
        }
    }

    @RequestMapping(value = "/alllatestdata/{deviceId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ListenableFuture<List<TsKvEntry>> getlatestData(@PathVariable("deviceId") String deviceId)
    throws DeviceAccessException{
        try{
            ListenableFuture<List<TsKvEntry>> tskventry = baseTimeseriesService.findAllLatest(toUUID(deviceId));
            return tskventry;
        }catch(Exception e){
            throw handleException(e);
        }
    }


    @RequestMapping(value = "/latestdata/{deviceId}/{keys}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ListenableFuture<List<TsKvEntry>> getlatestData(@PathVariable("deviceId") String deviceId
    ,@PathVariable("keys") Collection<String> keys)
            throws DeviceAccessException{
        try{
            ListenableFuture<List<TsKvEntry>> tskventry = baseTimeseriesService.findLatest(toUUID(deviceId), keys);
            return tskventry;
        }catch(Exception e){
            throw handleException(e);
        }
    }

}