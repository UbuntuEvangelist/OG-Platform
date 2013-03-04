/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.swap;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.id.ExternalId;

/**
 * Floating leg for variance swaps.
 */
@BeanDefinition
public class FloatingVarianceSwapLeg extends VarianceSwapLeg {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The ID of the underlying.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _underlyingId;
  /**
   * The monitoring frequency.
   */
  @PropertyDefinition
  private Frequency _monitoringFrequency;
  /**
   * The annualization factor.
   */
  @PropertyDefinition
  private Double _annualizationFactor;

  /**
   * Creates an instance.
   * 
   * @param dayCount The day count convention, not null
   * @param paymentFrequency The payment frequency, not null
   * @param regionId The region ID, not null
   * @param businessDayConvention The business day convention, not null
   * @param notional The notional, not null
   * @param eom The end-of-month flag
   * @param underlyingId The ID of the underlying, not null
   * @param monitoringFrequency The monitoring frequency
   * @param annualizationFactor The annualization factor
   */
  public FloatingVarianceSwapLeg(final DayCount dayCount,
      final Frequency paymentFrequency,
      final ExternalId regionId,
      final BusinessDayConvention businessDayConvention,
      final Notional notional,
      final boolean eom,
      final ExternalId underlyingId,
      final Frequency monitoringFrequency,
      final Double annualizationFactor) {
    super(dayCount, paymentFrequency, regionId, businessDayConvention, notional, eom);
    setUnderlyingId(underlyingId);
    setMonitoringFrequency(monitoringFrequency);
    setAnnualizationFactor(annualizationFactor);
  }

  /**
   * Creates an empty instance.
   */
  protected FloatingVarianceSwapLeg() {
  }

  @Override
  public <T> T accept(final SwapLegVisitor<T> visitor) {
    return visitor.visitFloatingVarianceSwapLeg(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FloatingVarianceSwapLeg}.
   * @return the meta-bean, not null
   */
  public static FloatingVarianceSwapLeg.Meta meta() {
    return FloatingVarianceSwapLeg.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(FloatingVarianceSwapLeg.Meta.INSTANCE);
  }

  @Override
  public FloatingVarianceSwapLeg.Meta metaBean() {
    return FloatingVarianceSwapLeg.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -771625640:  // underlyingId
        return getUnderlyingId();
      case -1233038860:  // monitoringFrequency
        return getMonitoringFrequency();
      case 663363412:  // annualizationFactor
        return getAnnualizationFactor();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -771625640:  // underlyingId
        setUnderlyingId((ExternalId) newValue);
        return;
      case -1233038860:  // monitoringFrequency
        setMonitoringFrequency((Frequency) newValue);
        return;
      case 663363412:  // annualizationFactor
        setAnnualizationFactor((Double) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_underlyingId, "underlyingId");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FloatingVarianceSwapLeg other = (FloatingVarianceSwapLeg) obj;
      return JodaBeanUtils.equal(getUnderlyingId(), other.getUnderlyingId()) &&
          JodaBeanUtils.equal(getMonitoringFrequency(), other.getMonitoringFrequency()) &&
          JodaBeanUtils.equal(getAnnualizationFactor(), other.getAnnualizationFactor()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMonitoringFrequency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getAnnualizationFactor());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the ID of the underlying.
   * @return the value of the property, not null
   */
  public ExternalId getUnderlyingId() {
    return _underlyingId;
  }

  /**
   * Sets the ID of the underlying.
   * @param underlyingId  the new value of the property, not null
   */
  public void setUnderlyingId(ExternalId underlyingId) {
    JodaBeanUtils.notNull(underlyingId, "underlyingId");
    this._underlyingId = underlyingId;
  }

  /**
   * Gets the the {@code underlyingId} property.
   * @return the property, not null
   */
  public final Property<ExternalId> underlyingId() {
    return metaBean().underlyingId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the monitoring frequency.
   * @return the value of the property
   */
  public Frequency getMonitoringFrequency() {
    return _monitoringFrequency;
  }

  /**
   * Sets the monitoring frequency.
   * @param monitoringFrequency  the new value of the property
   */
  public void setMonitoringFrequency(Frequency monitoringFrequency) {
    this._monitoringFrequency = monitoringFrequency;
  }

  /**
   * Gets the the {@code monitoringFrequency} property.
   * @return the property, not null
   */
  public final Property<Frequency> monitoringFrequency() {
    return metaBean().monitoringFrequency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the annualization factor.
   * @return the value of the property
   */
  public Double getAnnualizationFactor() {
    return _annualizationFactor;
  }

  /**
   * Sets the annualization factor.
   * @param annualizationFactor  the new value of the property
   */
  public void setAnnualizationFactor(Double annualizationFactor) {
    this._annualizationFactor = annualizationFactor;
  }

  /**
   * Gets the the {@code annualizationFactor} property.
   * @return the property, not null
   */
  public final Property<Double> annualizationFactor() {
    return metaBean().annualizationFactor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FloatingVarianceSwapLeg}.
   */
  public static class Meta extends VarianceSwapLeg.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code underlyingId} property.
     */
    private final MetaProperty<ExternalId> _underlyingId = DirectMetaProperty.ofReadWrite(
        this, "underlyingId", FloatingVarianceSwapLeg.class, ExternalId.class);
    /**
     * The meta-property for the {@code monitoringFrequency} property.
     */
    private final MetaProperty<Frequency> _monitoringFrequency = DirectMetaProperty.ofReadWrite(
        this, "monitoringFrequency", FloatingVarianceSwapLeg.class, Frequency.class);
    /**
     * The meta-property for the {@code annualizationFactor} property.
     */
    private final MetaProperty<Double> _annualizationFactor = DirectMetaProperty.ofReadWrite(
        this, "annualizationFactor", FloatingVarianceSwapLeg.class, Double.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "underlyingId",
        "monitoringFrequency",
        "annualizationFactor");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -771625640:  // underlyingId
          return _underlyingId;
        case -1233038860:  // monitoringFrequency
          return _monitoringFrequency;
        case 663363412:  // annualizationFactor
          return _annualizationFactor;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends FloatingVarianceSwapLeg> builder() {
      return new DirectBeanBuilder<FloatingVarianceSwapLeg>(new FloatingVarianceSwapLeg());
    }

    @Override
    public Class<? extends FloatingVarianceSwapLeg> beanType() {
      return FloatingVarianceSwapLeg.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code underlyingId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> underlyingId() {
      return _underlyingId;
    }

    /**
     * The meta-property for the {@code monitoringFrequency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Frequency> monitoringFrequency() {
      return _monitoringFrequency;
    }

    /**
     * The meta-property for the {@code annualizationFactor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> annualizationFactor() {
      return _annualizationFactor;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
