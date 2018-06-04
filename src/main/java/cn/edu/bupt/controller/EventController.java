package cn.edu.bupt.controller;

import cn.edu.bupt.dao.page.TimePageData;
import cn.edu.bupt.dao.page.TimePageLink;
import cn.edu.bupt.pojo.event.Event;
import cn.edu.bupt.utils.StringUtil;
import com.google.gson.JsonArray;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EventController extends BaseController{

    //通过ID查找事件
    @RequestMapping(value = "/event/{tenantId}/{deviceId}", method = RequestMethod.GET)
    public TimePageData<Event> getEventById(@PathVariable("deviceId") String deviceId,
                                            @PathVariable("tenantId") Integer tenantId,
                                            @RequestParam int limit,
                                            @RequestParam long startTime,
                                            @RequestParam long endTime,
                                            @RequestParam(required = false) boolean ascOrder) throws Exception {
        if (StringUtil.isEmpty(deviceId)) {
            throw new Exception("can't be empty");
        }
        try {

            TimePageLink pageLink = new TimePageLink(limit,startTime,endTime,ascOrder);
            TimePageData<Event> event = baseEventService.findEvents(tenantId, deviceId, pageLink);
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}