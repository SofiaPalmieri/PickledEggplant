package com.olenickglobal.configuration;

import com.olenickglobal.entities.SUT;
import io.cucumber.core.backend.ObjectFactory;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.behaviors.Cached;
import org.picocontainer.lifecycle.DefaultLifecycleState;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class SUTAwarePicoFactory implements ObjectFactory {
    private final Set<Class<?>> classes = new HashSet<>();
    private MutablePicoContainer pico;

    private static boolean isInstantiable(Class<?> clazz) {
        boolean isNonStaticInnerClass = !Modifier.isStatic(clazz.getModifiers()) && clazz.getEnclosingClass() != null;
        return Modifier.isPublic(clazz.getModifiers()) && !Modifier.isAbstract(clazz.getModifiers()) && !isNonStaticInnerClass;
    }

    @Override
    public void start() {
        if (pico == null) {
            pico = new PicoBuilder().withCaching().withLifecycle().build();
            // This below is what makes this PicoFactory a "SUT-aware" one:
            pico.addComponent(SUT.class, SUT.getInstance());
            classes.add(SUT.class);
            classes.forEach(clazz -> {
                if (clazz != SUT.class) {
                    pico.addComponent(clazz);
                }
            });
        } else {
            // we already get a pico container which is in "disposed" lifecycle,
            // so recycle it by defining a new lifecycle and removing all instances.
            pico.setLifecycleState(new DefaultLifecycleState());
            // TODO: See if we need to add a SUT instance here too.
            pico.getComponentAdapters().forEach(cached -> ((Cached<?>) cached).flush());
        }
        pico.start();
    }

    @Override
    public void stop() {
        pico.stop();
        pico.dispose();
    }

    @Override
    public boolean addClass(Class<?> clazz) {
        boolean result = isInstantiable(clazz);
        if (!result) {
            result = classes.contains(clazz);
        } else if (classes.add(clazz)) {
            addConstructorDependencies(clazz);
        }
        return result;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return pico.getComponent(type);
    }

    private void addConstructorDependencies(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            for (Class<?> paramClazz : constructor.getParameterTypes()) {
                addClass(paramClazz);
            }
        }
    }
}
