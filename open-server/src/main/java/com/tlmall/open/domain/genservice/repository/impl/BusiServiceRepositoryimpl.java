package com.tlmall.open.domain.genservice.repository.impl;

import com.tlmall.open.domain.genservice.adaptor.GsService;
import com.tlmall.open.domain.genservice.repository.BusiServiceRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author ：楼兰
 * @description:
 **/

@Component
public class BusiServiceRepositoryimpl implements BusiServiceRepository {

    private List<GsService> busiServices = new ArrayList<>();
    @PostConstruct
    public void loadBusiServices() {
        if (busiServices.isEmpty()) {
            final ServiceLoader<GsService> gsServices = ServiceLoader.load(GsService.class);
            final Iterator<GsService> iterator = gsServices.iterator();
            while (iterator.hasNext()) {
                busiServices.add(iterator.next());
            }
        }
    }

    public GsService getGsService(String serviceCode) {
        for (GsService gsService : busiServices) {
            if (serviceCode.equals(gsService.serviceCode())) {
                return gsService;
            }
        }
        return null;
    }
}
