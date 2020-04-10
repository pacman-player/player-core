package core.app.service.impl;

import core.app.service.abstraction.GenericService;
import core.app.service.abstraction.TrashApiService;
import core.app.service.abstraction.TrashService;
import core.app.util.GenericHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

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
