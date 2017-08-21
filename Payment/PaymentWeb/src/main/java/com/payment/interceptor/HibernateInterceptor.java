package com.payment.interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.mapper.PaymentMapper;
import com.payment.mapper.PaymentMapperImpl;

@Component
public class HibernateInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = -2576433652022725199L;

  private static final Logger log = LoggerFactory.getLogger(HibernateInterceptor.class);

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  private static SimpMessagingTemplate staticMessagingTemplate;

  private PaymentMapper paymentMapper = new PaymentMapperImpl();

  private Set<Object> set = new HashSet<>();

  private Map<String, Object> headersMap = new HashMap<>();

  public HibernateInterceptor() {
    log.info("HibernateInterceptor object created*****");
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    log.info("object class: {}", tx.getClass().getSimpleName());
    log.info("afterTransactionCompletion: tx: {}, set size: {}", tx, set.size());
    for (Object obj : set) {
      log.info("Pushing object in set: {}", obj.getClass().getCanonicalName());
      log.info("messagingTemplate: {}", staticMessagingTemplate);
      log.info("object class: {}", obj.getClass().getSimpleName());
      headersMap.put("class", obj.getClass().getSimpleName());
      staticMessagingTemplate.convertAndSend("/topic/updates", obj, headersMap);
    }
    // clear set after push
    set.clear();
  }

  @Override
  public void onCollectionUpdate(Object collection, Serializable key) {
    updateSet(collection);
    log.info("onCollectionUpdate: collection: {}, key:{}", collection, key);
  }

  @Override
  public void onCollectionRecreate(Object collection, Serializable key) {
    updateSet(collection);
    log.info("onCollectionRecreate: collection: {}, key:{}", collection, key);
  }

  @Override
  public void onCollectionRemove(Object collection, Serializable key) {
    updateSet(collection);
    log.info("onCollectionRemove: collection: {}, key:{}", collection, key);
  }

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    updateSet(entity);
    log.info("onSave: entity: {}, id:{}", entity, id);
    return false;

  }

  private void updateSet(Object obj) {
    Object mappedObject = obj;
    if (obj instanceof PaymentSystemUsageDetails) {
      // We are not using raw PaymentSystemUsageDetails in UI, so map it to PaymentSystem and push
      // PaymentSystem
      mappedObject = paymentMapper.convertSystemUsageToSystem((PaymentSystemUsageDetails) obj);
    } else if (obj instanceof PaymentSystem) {
      mappedObject = paymentMapper.mapPaymentSystem((PaymentSystem) obj, true);
    }
    set.add(mappedObject);
  }

  /**
   * Set messagingTemplate to staticMessagingTemplate. messagingTemplate is spring bean, but this is
   * not injecting to interceptor which is registered by hibernate. So maintain static variable in
   * interceptor which is registered by hibernate.
   */
  @PostConstruct
  public void postConstruct() {
    staticMessagingTemplate = messagingTemplate;
  }
}
