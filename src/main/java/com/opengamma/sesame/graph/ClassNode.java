/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.sesame.config.ConfigUtils;
import com.opengamma.sesame.engine.ComponentMap;
import com.opengamma.sesame.function.Parameter;
import com.opengamma.util.ArgumentChecker;

/**
 * A node in the dependency model an object referred to by its concrete class that must be created by the injection framework.
 */
public class ClassNode extends DependentNode {

  private final Class<?> _implementationType;
  private Constructor<?> _constructor;

  public ClassNode(Class<?> type, Class<?> implementationType, List<Node> arguments, Parameter parameter) {
    super(type, parameter, arguments);
    _implementationType = ArgumentChecker.notNull(implementationType, "implementationType");
    _constructor = ConfigUtils.getConstructor(_implementationType);
  }

  @Override
  protected Object doCreate(ComponentMap componentMap, List<Object> dependencies) {
    try {
      return _constructor.newInstance(dependencies.toArray());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new OpenGammaRuntimeException("Failed to create of " + _constructor.getDeclaringClass().getName(), e);
    }
  }

  public Class<?> getImplementationType() {
    return _implementationType;
  }

  @Override
  public String prettyPrint() {
    return getParameterName() + "new " + _implementationType.getSimpleName();
  }

  @Override
  public int hashCode() {
    return 31 * super.hashCode() + Objects.hash(_implementationType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    final ClassNode other = (ClassNode) obj;
    return Objects.equals(this._implementationType, other._implementationType);
  }
}
