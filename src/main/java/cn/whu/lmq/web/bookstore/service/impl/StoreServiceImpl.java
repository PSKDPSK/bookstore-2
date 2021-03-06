package cn.whu.lmq.web.bookstore.service.impl;

import cn.whu.lmq.web.bookstore.bean.Store;
import cn.whu.lmq.web.bookstore.dao.StoreDao;
import cn.whu.lmq.web.bookstore.service.StoreService;
import cn.whu.lmq.web.bookstore.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {
    private StoreDao storeDao;

    @Autowired
    public void setStoreDao(StoreDao storeDao) {
        this.storeDao = storeDao;
    }


    /**
     * 如果书店已经存在，则更新，
     * 不存在则保存
     */
    @Override
    public void saveOrUpdate(Store store) {
        //store不存在时
        if (getUniqueStore() == null) {
            storeDao.save(store);
            return;
        }
        Store exist = storeDao.findById(store);
        CopyUtil.copyNotNullFields(store,exist);
        //更新
        storeDao.update(exist);
    }

    @Override
    public Store getUniqueStore() {
        List<Store> stores = storeDao.findByCriteria(criteria -> {
        });
        if (stores == null || stores.isEmpty()) {
            return null;
        }
        return stores.get(0);
    }


}
