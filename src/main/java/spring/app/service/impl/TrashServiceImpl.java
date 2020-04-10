package spring.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.GenericService;
import spring.app.service.abstraction.TrashApiService;
import spring.app.service.abstraction.TrashService;
import spring.app.util.GenericHelper;

import java.io.IOException;

@Service
public class TrashServiceImpl<E> implements TrashService<E> {

    private Logger LOGGER = LoggerFactory.getLogger(TrashServiceImpl.class);
    private BeanFactory beanFactory;
    private TrashApiService<E> apiService;
    private GenericHelper<E> genericHelper;

    public TrashServiceImpl(BeanFactory beanFactory,
                            TrashApiService<E> apiService,
                            GenericHelper<E> genericHelper) {
        this.beanFactory = beanFactory;
        this.apiService = apiService;
        this.genericHelper = genericHelper;
    }

    @Override
    public void moveToTrash(E entity) throws IOException, InterruptedException {
        String entityClassNameInLowerCase = genericHelper.getEntityClassNameInLowerCase(entity);
        String beanName = entityClassNameInLowerCase + "ServiceImpl";
        GenericService<E> service = (GenericService<E>) beanFactory.getBean(beanName);
        if (apiService.moveToTrash(entity)) {
            service.deleteEntity(entity);
            LOGGER.info("Entity " + entityClassNameInLowerCase + " moved to trash.");
        }
    }
}
