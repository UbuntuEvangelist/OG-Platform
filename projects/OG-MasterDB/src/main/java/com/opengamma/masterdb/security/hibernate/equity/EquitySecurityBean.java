/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.security.hibernate.equity;

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.masterdb.security.hibernate.CurrencyBean;
import com.opengamma.masterdb.security.hibernate.ExchangeBean;
import com.opengamma.masterdb.security.hibernate.SecurityBean;

/**
 * A Hibernate bean representation of {@link EquitySecurityBean}.
 */
@BeanDefinition
public class EquitySecurityBean extends SecurityBean {

  @PropertyDefinition
  private ExchangeBean _exchange;
  @PropertyDefinition
  private String _shortName;
  @PropertyDefinition
  private String _companyName;
  @PropertyDefinition
  private CurrencyBean _currency;
  @PropertyDefinition
  private GICSCodeBean _gicsCode;
  @PropertyDefinition
  private boolean _preferred;

  public boolean equals(Object other) {
    if (!(other instanceof EquitySecurityBean)) {
      return false;
    }
    EquitySecurityBean equity = (EquitySecurityBean) other;
    if (getId() != -1 && equity.getId() != -1) {
      return getId().longValue() == equity.getId().longValue();
    }
    return new EqualsBuilder().append(getExchange(), equity.getExchange())
                              .append(getCompanyName(), equity.getCompanyName())
                              .append(getCurrency(), equity.getCurrency()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getExchange())
                                .append(getCompanyName())
                                .append(getCurrency())
                                .toHashCode(); 
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code EquitySecurityBean}.
   * @return the meta-bean, not null
   */
  public static EquitySecurityBean.Meta meta() {
    return EquitySecurityBean.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(EquitySecurityBean.Meta.INSTANCE);
  }

  @Override
  public EquitySecurityBean.Meta metaBean() {
    return EquitySecurityBean.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1989774883:  // exchange
        return getExchange();
      case -2028219097:  // shortName
        return getShortName();
      case -508582744:  // companyName
        return getCompanyName();
      case 575402001:  // currency
        return getCurrency();
      case 762040799:  // gicsCode
        return getGicsCode();
      case -1294005119:  // preferred
        return isPreferred();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1989774883:  // exchange
        setExchange((ExchangeBean) newValue);
        return;
      case -2028219097:  // shortName
        setShortName((String) newValue);
        return;
      case -508582744:  // companyName
        setCompanyName((String) newValue);
        return;
      case 575402001:  // currency
        setCurrency((CurrencyBean) newValue);
        return;
      case 762040799:  // gicsCode
        setGicsCode((GICSCodeBean) newValue);
        return;
      case -1294005119:  // preferred
        setPreferred((Boolean) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange.
   * @return the value of the property
   */
  public ExchangeBean getExchange() {
    return _exchange;
  }

  /**
   * Sets the exchange.
   * @param exchange  the new value of the property
   */
  public void setExchange(ExchangeBean exchange) {
    this._exchange = exchange;
  }

  /**
   * Gets the the {@code exchange} property.
   * @return the property, not null
   */
  public final Property<ExchangeBean> exchange() {
    return metaBean().exchange().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the shortName.
   * @return the value of the property
   */
  public String getShortName() {
    return _shortName;
  }

  /**
   * Sets the shortName.
   * @param shortName  the new value of the property
   */
  public void setShortName(String shortName) {
    this._shortName = shortName;
  }

  /**
   * Gets the the {@code shortName} property.
   * @return the property, not null
   */
  public final Property<String> shortName() {
    return metaBean().shortName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the companyName.
   * @return the value of the property
   */
  public String getCompanyName() {
    return _companyName;
  }

  /**
   * Sets the companyName.
   * @param companyName  the new value of the property
   */
  public void setCompanyName(String companyName) {
    this._companyName = companyName;
  }

  /**
   * Gets the the {@code companyName} property.
   * @return the property, not null
   */
  public final Property<String> companyName() {
    return metaBean().companyName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property
   */
  public CurrencyBean getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property
   */
  public void setCurrency(CurrencyBean currency) {
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<CurrencyBean> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the gicsCode.
   * @return the value of the property
   */
  public GICSCodeBean getGicsCode() {
    return _gicsCode;
  }

  /**
   * Sets the gicsCode.
   * @param gicsCode  the new value of the property
   */
  public void setGicsCode(GICSCodeBean gicsCode) {
    this._gicsCode = gicsCode;
  }

  /**
   * Gets the the {@code gicsCode} property.
   * @return the property, not null
   */
  public final Property<GICSCodeBean> gicsCode() {
    return metaBean().gicsCode().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the preferred.
   * @return the value of the property
   */
  public boolean isPreferred() {
    return _preferred;
  }

  /**
   * Sets the preferred.
   * @param preferred  the new value of the property
   */
  public void setPreferred(boolean preferred) {
    this._preferred = preferred;
  }

  /**
   * Gets the the {@code preferred} property.
   * @return the property, not null
   */
  public final Property<Boolean> preferred() {
    return metaBean().preferred().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code EquitySecurityBean}.
   */
  public static class Meta extends SecurityBean.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code exchange} property.
     */
    private final MetaProperty<ExchangeBean> _exchange = DirectMetaProperty.ofReadWrite(
        this, "exchange", EquitySecurityBean.class, ExchangeBean.class);
    /**
     * The meta-property for the {@code shortName} property.
     */
    private final MetaProperty<String> _shortName = DirectMetaProperty.ofReadWrite(
        this, "shortName", EquitySecurityBean.class, String.class);
    /**
     * The meta-property for the {@code companyName} property.
     */
    private final MetaProperty<String> _companyName = DirectMetaProperty.ofReadWrite(
        this, "companyName", EquitySecurityBean.class, String.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<CurrencyBean> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", EquitySecurityBean.class, CurrencyBean.class);
    /**
     * The meta-property for the {@code gicsCode} property.
     */
    private final MetaProperty<GICSCodeBean> _gicsCode = DirectMetaProperty.ofReadWrite(
        this, "gicsCode", EquitySecurityBean.class, GICSCodeBean.class);
    /**
     * The meta-property for the {@code preferred} property.
     */
    private final MetaProperty<Boolean> _preferred = DirectMetaProperty.ofReadWrite(
        this, "preferred", EquitySecurityBean.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "exchange",
        "shortName",
        "companyName",
        "currency",
        "gicsCode",
        "preferred");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1989774883:  // exchange
          return _exchange;
        case -2028219097:  // shortName
          return _shortName;
        case -508582744:  // companyName
          return _companyName;
        case 575402001:  // currency
          return _currency;
        case 762040799:  // gicsCode
          return _gicsCode;
        case -1294005119:  // preferred
          return _preferred;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends EquitySecurityBean> builder() {
      return new DirectBeanBuilder<EquitySecurityBean>(new EquitySecurityBean());
    }

    @Override
    public Class<? extends EquitySecurityBean> beanType() {
      return EquitySecurityBean.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code exchange} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExchangeBean> exchange() {
      return _exchange;
    }

    /**
     * The meta-property for the {@code shortName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> shortName() {
      return _shortName;
    }

    /**
     * The meta-property for the {@code companyName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> companyName() {
      return _companyName;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<CurrencyBean> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code gicsCode} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<GICSCodeBean> gicsCode() {
      return _gicsCode;
    }

    /**
     * The meta-property for the {@code preferred} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> preferred() {
      return _preferred;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
