/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.ModuleComponent;
import org.alfresco.repo.module.AbstractModuleComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <code>BeanPostProcessor</code> for {@link ModuleComponent} annotation.
 *
 * @author Andrea Como
 */
@Component
public class ModuleComponentConfigurer implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        if (bean instanceof AbstractModuleComponent component && bean.getClass().getAnnotation(ModuleComponent.class) != null) {
            ModuleComponent componentAnnotation = bean.getClass().getAnnotation(ModuleComponent.class);

            component.setModuleId(componentAnnotation.moduleId());
            component.setName(componentAnnotation.name());
            component.setDescription(componentAnnotation.description());
            component.setSinceVersion(componentAnnotation.sinceVersion());
            component.setAppliesFromVersion(componentAnnotation.appliesFromVersion());
            component.setExecuteOnceOnly(componentAnnotation.executeOnceOnly());

            for (String dependOn : componentAnnotation.dependsOn()) {
                org.alfresco.repo.module.ModuleComponent moduleComponent = applicationContext.getBean(dependOn, org.alfresco.repo.module.ModuleComponent.class);
                component.getDependsOn().add(moduleComponent);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
