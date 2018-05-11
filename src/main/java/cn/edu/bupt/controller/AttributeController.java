package cn.edu.bupt.controller;



import cn.edu.bupt.pojo.kv.AttributeKvEntry;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class AttributeController extends BaseController{

    //通过设备ID获取全部属性
    @RequestMapping(value="/allattributes/{deviceId}", method = RequestMethod.GET)
    public List<AttributeKvEntry> getAllAttributes(@PathVariable("deviceId") String deviceId) throws Exception {
        try{
            ListenableFuture<List<AttributeKvEntry>> attributeKvEntry = baseAttributesService.findAll(toUUID(deviceId));
            List<AttributeKvEntry> ls = attributeKvEntry.get();
            return ls;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //设备ID和属性键获取设备属性（多个）
    @RequestMapping(value="/attributes/{deviceId}/{attributeKeys}", method = RequestMethod.GET)
    public List<AttributeKvEntry> getAttribute(
            @PathVariable("deviceId") String deviceId, @PathVariable("attributeKeys") Collection<String> attributeKeys) throws Exception {
        try {
            ListenableFuture<List<AttributeKvEntry>> listListenableFuture =
                    baseAttributesService.find(toUUID(deviceId), attributeKeys);
            List<AttributeKvEntry> ls = listListenableFuture.get();
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //设备ID和属性键获取设备属性（单个）
    @RequestMapping(value="/attribute/{deviceId}/{attributeKey}", method = RequestMethod.GET)
        public Optional<AttributeKvEntry> getAttribute(
                @PathVariable("deviceId") String deviceId, @PathVariable("attributeKey") String attributeKey) throws Exception {
        try {
            ListenableFuture<Optional<AttributeKvEntry>> optionalListenableFuture =
                    baseAttributesService.find(toUUID(deviceId), attributeKey);
            Optional<AttributeKvEntry> ls = optionalListenableFuture.get();
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除属性的键值
    @RequestMapping(value="/allattributes/{deviceId}/{keys}",method = RequestMethod.DELETE)
    public void removeAllAttributes(
            @PathVariable("deviceId") String deviceId, @PathVariable("keys") List<String> keys) throws Exception{
        try{
            baseAttributesService.removeAll(toUUID(deviceId), keys);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


