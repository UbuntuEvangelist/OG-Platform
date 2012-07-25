/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.masterdb.security.hibernate.cds;

import static com.opengamma.masterdb.security.hibernate.Converters.currencyBeanToCurrency;
import static com.opengamma.masterdb.security.hibernate.Converters.dateTimeWithZoneToZonedDateTimeBean;
import static com.opengamma.masterdb.security.hibernate.Converters.externalIdBeanToExternalId;
import static com.opengamma.masterdb.security.hibernate.Converters.externalIdToExternalIdBean;
import static com.opengamma.masterdb.security.hibernate.Converters.frequencyBeanToFrequency;
import static com.opengamma.masterdb.security.hibernate.Converters.zonedDateTimeBeanToDateTimeWithZone;

import com.opengamma.financial.security.cds.CDSSecurity;
import com.opengamma.masterdb.security.hibernate.AbstractSecurityBeanOperation;
import com.opengamma.masterdb.security.hibernate.HibernateSecurityMasterDao;
import com.opengamma.masterdb.security.hibernate.OperationContext;

/**
 * Operation for converting between {@link CDSSecurity} and {@link CDSSecurityBean}
 * @author Martin Traverse
 */
public final class CDSSecurityBeanOperation extends AbstractSecurityBeanOperation<CDSSecurity, CDSSecurityBean> {

  /** Singleton instance */
  public static final CDSSecurityBeanOperation INSTANCE = new CDSSecurityBeanOperation();
  
  private CDSSecurityBeanOperation() {
    super(CDSSecurity.SECURITY_TYPE, CDSSecurity.class, CDSSecurityBean.class);
  }

  @Override
  public CDSSecurityBean createBean(OperationContext context, HibernateSecurityMasterDao secMasterSession, CDSSecurity security) {
    final CDSSecurityBean bean = new CDSSecurityBean();
    bean.setNotional(security.getNotional());
    bean.setRecoveryRate(security.getRecoveryRate());
    bean.setPremiumRate(security.getPremiumRate());
    bean.setCurrency(secMasterSession.getOrCreateCurrencyBean(security.getCurrency().getCode()));
    bean.setMaturity(dateTimeWithZoneToZonedDateTimeBean(security.getMaturity()));
    bean.setFirstPremiumDate(dateTimeWithZoneToZonedDateTimeBean(security.getFirstPremiumDate()));
    bean.setPremiumFrequency(secMasterSession.getOrCreateFrequencyBean(security.getPremiumFrequency().getConventionName()));
    bean.setUnderlying(externalIdToExternalIdBean(security.getUnderlying()));
    return bean;
  }

  @Override
  public CDSSecurity createSecurity(OperationContext context, CDSSecurityBean bean) {
    return new CDSSecurity(
      bean.getNotional(),
      bean.getRecoveryRate(),
      bean.getPremiumRate(),
      currencyBeanToCurrency(bean.getCurrency()),
      zonedDateTimeBeanToDateTimeWithZone(bean.getMaturity()),
      zonedDateTimeBeanToDateTimeWithZone(bean.getFirstPremiumDate()),
      frequencyBeanToFrequency(bean.getPremiumFrequency()),
      externalIdBeanToExternalId(bean.getUnderlying())
    );
  }
}
