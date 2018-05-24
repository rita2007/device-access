package cn.edu.bupt.dao.device;

import cn.edu.bupt.dao.Dao;
import cn.edu.bupt.dao.page.TextPageLink;
import cn.edu.bupt.pojo.Device;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by CZX on 2018/4/17.
 */
public interface DeviceDao extends Dao<Device> {

    /**
     * Save or update device object
     *
     * @param device the device object
     * @return saved device object
     */
    Device save(Device device);

    /**
     * Find devices by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantId(Integer tenantId, TextPageLink pageLink);


    /**
     * Find devices by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param pageLink the page link
     * @return the list of device objects
     */
    List<Device> findDevicesByTenantIdAndCustomerId(Integer tenantId, Integer customerId, TextPageLink pageLink);

    /**
     * Find devices by tenantId and device name.
     *
     * @param tenantId the tenantId
     * @param name the device name
     * @return the optional device object
     */
    Optional<Device> findDeviceByTenantIdAndName(Integer tenantId, String name);


    List<Device> findDevicesByParentDeviceId(String parentDeviceId, TextPageLink pageLink);

    List<Device> findDevicesByManufactureAndDeviceTypeAndModel(String manufacture, String deviceType, String model, TextPageLink pageLink);
}
