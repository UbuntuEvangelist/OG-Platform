/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.bbg.component;

import java.util.LinkedHashMap;
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

import com.opengamma.bbg.referencedata.ReferenceDataProvider;
import com.opengamma.bbg.referencedata.impl.PatchableReferenceDataProvider;
import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;

/**
 * Replaces a standard reference data provider with a patchable one.
 */
@BeanDefinition
public class PatchableReferenceDataProviderComponentFactory extends AbstractComponentFactory {

  /**
   * The Bloomberg reference data provider.
   */
  @PropertyDefinition(validate = "notNull")
  private ReferenceDataProvider _referenceDataProvider;

  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) {
    ReferenceDataProvider wrappedRefData = new PatchableReferenceDataProvider(getReferenceDataProvider());
    ComponentInfo info = new ComponentInfo(ReferenceDataProvider.class, "bloomberg");
    repo.registerComponent(info, wrappedRefData);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code PatchableReferenceDataProviderComponentFactory}.
   * @return the meta-bean, not null
   */
  public static PatchableReferenceDataProviderComponentFactory.Meta meta() {
    return PatchableReferenceDataProviderComponentFactory.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(PatchableReferenceDataProviderComponentFactory.Meta.INSTANCE);
  }

  @Override
  public PatchableReferenceDataProviderComponentFactory.Meta metaBean() {
    return PatchableReferenceDataProviderComponentFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1788671322:  // referenceDataProvider
        return getReferenceDataProvider();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1788671322:  // referenceDataProvider
        setReferenceDataProvider((ReferenceDataProvider) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_referenceDataProvider, "referenceDataProvider");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      PatchableReferenceDataProviderComponentFactory other = (PatchableReferenceDataProviderComponentFactory) obj;
      return JodaBeanUtils.equal(getReferenceDataProvider(), other.getReferenceDataProvider()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getReferenceDataProvider());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the Bloomberg reference data provider.
   * @return the value of the property, not null
   */
  public ReferenceDataProvider getReferenceDataProvider() {
    return _referenceDataProvider;
  }

  /**
   * Sets the Bloomberg reference data provider.
   * @param referenceDataProvider  the new value of the property, not null
   */
  public void setReferenceDataProvider(ReferenceDataProvider referenceDataProvider) {
    JodaBeanUtils.notNull(referenceDataProvider, "referenceDataProvider");
    this._referenceDataProvider = referenceDataProvider;
  }

  /**
   * Gets the the {@code referenceDataProvider} property.
   * @return the property, not null
   */
  public final Property<ReferenceDataProvider> referenceDataProvider() {
    return metaBean().referenceDataProvider().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code PatchableReferenceDataProviderComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code referenceDataProvider} property.
     */
    private final MetaProperty<ReferenceDataProvider> _referenceDataProvider = DirectMetaProperty.ofReadWrite(
        this, "referenceDataProvider", PatchableReferenceDataProviderComponentFactory.class, ReferenceDataProvider.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "referenceDataProvider");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1788671322:  // referenceDataProvider
          return _referenceDataProvider;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends PatchableReferenceDataProviderComponentFactory> builder() {
      return new DirectBeanBuilder<PatchableReferenceDataProviderComponentFactory>(new PatchableReferenceDataProviderComponentFactory());
    }

    @Override
    public Class<? extends PatchableReferenceDataProviderComponentFactory> beanType() {
      return PatchableReferenceDataProviderComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code referenceDataProvider} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ReferenceDataProvider> referenceDataProvider() {
      return _referenceDataProvider;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
