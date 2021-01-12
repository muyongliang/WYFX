package com.wyfx.sf.repository;

import com.wyfx.sf.model.Gps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liu on 2017/9/18.
 */
public interface GpsRepo extends BaseRepo<Gps> {

    List<Gps> findByDeviceIdOrderByIdDesc(Long deviceId);

    Page<Gps> findByDeviceId(Long deviceId, Pageable page);

    @Query("select g from Gps g where g.deviceId = ?1 group by g.longitude,g.latitude")
    List<Gps> getGpsByDistinct(Long deviceId);

    Page<Gps> findByDeviceIdAndLongitudeAndLatitude(Long deviceId, Double longitude, Double latitude, Pageable page);

}
